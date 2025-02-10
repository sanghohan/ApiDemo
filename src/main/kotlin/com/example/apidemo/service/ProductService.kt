package com.example.apidemo.service

import com.example.apidemo.dto.ProductDto
import com.example.apidemo.dto.ProductReq
import com.example.apidemo.repository.ProductRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class ProductService (
    private val productRepository: ProductRepository
){
    fun addProduct(product: ProductReq) {

        productRepository.save(product.toEntity())

        logger.debug { "Product added: $product" }
    }

    fun getProducts() = productRepository.findAll().map {
        ProductDto(
            id = it.productId,
            name = it.name,
            price = it.price,
            category = it.category,
            seller = it.seller.toDto()
        )
    }.toList()
}