package com.example.apidemo.repository

import com.example.apidemo.repository.entity.SellerEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SellerRepository: JpaRepository<SellerEntity, Long> {

    @EntityGraph(attributePaths = ["products"])
    @Query("SELECT DISTINCT s FROM SellerEntity s LEFT JOIN FETCH s.products")
    fun findAllWithProducts(): List<SellerEntity>

    //@EntityGraph(attributePaths = ["products"])
    //override fun findAll(): List<SellerEntity>
}