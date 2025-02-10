package com.example.apidemo.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: HttpServletRequest): ResponseEntity<String> {
        val traceId = MDC.get("traceId")
        MDC.put("error_traceId", traceId)  // 에러 발생한 traceId를 MDC에 추가

        logger.error { "Exception occurred: ${ex.message}" }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred.")
    }
}