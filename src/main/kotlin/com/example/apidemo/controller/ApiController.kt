package com.example.apidemo.controller

import com.example.apidemo.dto.ProductDto
import com.example.apidemo.dto.ProductReq
import com.example.apidemo.service.ProductService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.lang.Thread.sleep

private val logger = KotlinLogging.logger {}

@RestController
class ApiController(
    private val productService: ProductService,
) {
    @GetMapping("/api/test/products")
    fun getProducts(): List<ProductDto> {
        return productService.getProducts()
    }

    @PostMapping("/api/test/product/add")
    fun addProduct(
        @RequestBody
        req: ProductReq,
    ): String {
        productService.addProduct(req)
        return "success"
    }

    @GetMapping("/api/test/error")
    fun error(): String {
        logger.debug { "debug error!!" }
        sleep(1000)
        logger.error { "error error!! after 1sec" }
        throw RuntimeException("error")
    }
}