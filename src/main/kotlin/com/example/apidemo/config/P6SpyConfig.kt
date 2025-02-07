package com.example.apidemo.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.logging.P6LogOptions
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*


@Profile(value = ["dev", "prd"])
@Configuration
class P6SpyConfig {

    @PostConstruct
    fun init() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyLineFormat::class.java.name
    }
}

@Profile(value = ["local"])
@Configuration
class P6SpyConfigLocal {

    @PostConstruct
    fun init() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyPrettyFormat::class.java.name
    }
}

class P6SpyLineFormat : MessageFormattingStrategy {

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String {

        return if (elapsed == 0L && Category.BATCH.name.equals(category)) {
            "[$category] | $elapsed ms"
        } else {
            "[$category] | $elapsed ms $sql"
        }
    }
}

class P6SpyPrettyFormat : MessageFormattingStrategy {

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String {

        return if (elapsed == 0L && Category.BATCH.name.equals(category)) {
            // add batch 모드 여서 그런지, 2번 로그가 기독되어 예외 처리 한다.
            "[$category] | $elapsed ms | $connectionId"
        } else {
            "[$category] | $elapsed ms | ${formatSql(category, sql)}"
        }
    }

    private fun isDDL(sql: String): Boolean =
        sql.startsWith("create")
            || sql.startsWith("alter")
            || sql.startsWith("comment")
            || sql.startsWith("drop")

    private fun highlight(sql: String): String = FormatStyle.HIGHLIGHT.formatter.format(sql)

    private fun formatSql(category: String?, sql: String?): String {
        val trim = sql.orEmpty().trim()
        return if (category in listOf(Category.RESULT.name, Category.RESULTSET.name)) {
            if(isDDL(trim)) highlight(FormatStyle.DDL.formatter.format(trim))
            else highlight(FormatStyle.BASIC.formatter.format(trim))
            } else trim
        }
}


@Configuration
class P6LogOptionsConfig(
    @Value("\${decorator.datasource.p6spy.enable-logging:false}")
    private val loggingEnable: Boolean,
) {

    @PostConstruct
    fun init() {

        if (!loggingEnable)
            return

        /**
         * ref. https://p6spy.readthedocs.io/en/latest/configandusage.html
         * 일부 값들은 properties 로 빼도 될꺼 같은데, 운영기 에서는 로깅을 꺼야 할꺼니.
         */
        val excludeCategories = listOf(
            "INFO", "DEBUG", "result", "resultset",
//            "batch",
//            "commit",
        ).joinToString(",")

        // 필요시 추가..
        val excludeFilter = listOf("").joinToString(",")

        P6LogOptions.getActiveInstance().let {
            it.excludecategories = excludeCategories
            it.filter = true
            it.exclude = excludeFilter
        }
    }
}
