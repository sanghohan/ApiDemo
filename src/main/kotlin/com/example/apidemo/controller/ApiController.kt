package com.example.apidemo.controller

import com.example.apidemo.dto.ProductDto
import com.example.apidemo.dto.ProductReq
import com.example.apidemo.service.ProductService
import org.springframework.web.bind.annotation.*

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
}