package com.example.apidemo.repository

import com.example.apidemo.dto.ProductSearchDto
import com.example.apidemo.repository.entity.ProductEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Long> {


    @Query("SELECT new com.example.apidemo.dto.ProductSearchDto(p.productId , p.name, p.price) FROM ProductEntity p")
    fun findAllProductDTOs(): List<ProductSearchDto>

    @Lock(LockModeType.PESSIMISTIC_READ)
    override fun findAll(): List<ProductEntity>
}