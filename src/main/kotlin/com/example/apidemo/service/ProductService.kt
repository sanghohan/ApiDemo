package com.example.apidemo.service

import com.example.apidemo.dto.ProductDto
import com.example.apidemo.dto.ProductReq
import com.example.apidemo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository
){
    fun addProduct(product: ProductReq) {

        productRepository.save(product.toEntity())
    }

    fun getProducts() = productRepository.findAll().map {
        ProductDto(
            id = it.productId.toString(),
            name = it.name,
            price = it.price,
            category = it.category,
            seller = it.seller.toDto()
        )
    }.toList()
}