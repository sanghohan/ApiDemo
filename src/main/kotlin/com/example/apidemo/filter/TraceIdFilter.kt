package com.example.apidemo.filter

import jakarta.servlet.*
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*


@Component
@Order(1)
class TraceIdFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val traceId = UUID.randomUUID().toString()
        MDC.put("traceId", traceId)
        try {
            chain.doFilter(request, response)
        } finally {
            MDC.remove("traceId") // 요청 처리 완료 후 MDC에서 제거
        }
    }

    override fun init(filterConfig: FilterConfig) {}
    override fun destroy() {}


}