package com.example.apidemo.controller

import com.example.apidemo.dto.ProductDto
import com.example.apidemo.repository.entity.SellerEntity
import com.example.apidemo.service.ProductService
import com.example.apidemo.support.IntegrationTest
import org.junit.jupiter.api.Test
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every

@IntegrationTest
class ControllerTest {

    @MockkBean
    private lateinit var productService: ProductService


    @Test
    fun controllerTest() {

        //seller given
        val seller1 = SellerEntity(
            sellerId = 1,
            name = "seller1",
            email = "seller1@email.com",
            phone = "010-1234-5678"
        )

        val seller2 = SellerEntity(
            sellerId = 2,
            name = "seller2",
            email = "seller2@ematil.com",
            phone = "010-1234-5678"
        )

        //given
        every { productService.getProducts() } returns listOf(
            ProductDto(id =1L, name = "test1", price = 1000.0, category = "game", seller = seller1.toDto()),
            ProductDto(id =2L, name = "test2", price = 2000.0, category = "vod", seller = seller2.toDto())
        )

        val result = ApiController(productService).getProducts().also {
            println(it.toString())
        }

        //then
        assert(result.size == 2)
        assert(result[0].name == "test1")
        assert(result[0].price == 1000.0)
        assert(result[1].name == "test2")
        assert(result[1].price == 2000.0)

    }
}