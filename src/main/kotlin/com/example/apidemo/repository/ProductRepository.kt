package com.example.apidemo.repository

import com.example.apidemo.dto.ProductSearchDto
import com.example.apidemo.repository.entity.ProductEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import kotlin.reflect.KClass

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Long> {


    @Query("SELECT new com.example.apidemo.dto.ProductSearchDto(p.productId , p.name, p.price) FROM ProductEntity p")
    fun findAllProductDTOs(): List<ProductSearchDto>

    fun <T> findByName(name: String, type: Class<T>): Collection<T>

    @Lock(LockModeType.PESSIMISTIC_READ)
    override fun findAll(): List<ProductEntity>

    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.productId = :productId")
    fun deleteByProductId(productId: Long): Int

    @Modifying
    @Query("UPDATE ProductEntity p SET p.name = :name, p.price = :price WHERE p.productId = :productId")
    fun updateByProductId(productId: Long, name: String, price: Double): Int



}