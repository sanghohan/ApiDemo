package com.example.apidemo.dto

import com.example.apidemo.repository.entity.ProductEntity

data class ProductReq(
    val name: String? = null,
    val price: Double? = 0.0,
    val category: String? = "uncategorized",
    val seller : SellerDto
) {
    fun toEntity() = ProductEntity(
        name = name,
        price = price,
        category = category,
        seller = seller.toEntity()
    )
}

data class ProductDto(
    val id: Long? = null,
    val name: String? = null,
    val price: Double? = 0.0,
    val category: String? = "uncategorized",
    val seller: SellerDto? = null
)

data class ProductOnlyNameDto(
    val name: String? = null
)

data class ProductSearchDto(
    val id: Long? = null,
    val name: String? = null,
    val price: Double? = 0.0,
)

interface BaseProductProjection {
    fun getProductId(): Long
    fun getName(): String
}

interface FullProductProjection : BaseProductProjection {
    fun getPrice(): Double
}