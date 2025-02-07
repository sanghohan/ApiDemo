package com.example.apidemo.dto

import com.example.apidemo.repository.entity.SellerEntity

data class SellerReq(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null
) {
    fun toEntity() = SellerEntity(
        name = name,
        email = email,
        phone = phone
    )
}

data class SellerDto(
    val sellerId: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null
) {
    fun toEntity() = SellerEntity(
        sellerId = sellerId,
        name = name,
        email = email,
        phone = phone
    )
}