package com.example.apidemo.repository

import com.example.apidemo.dto.*
import com.example.apidemo.repository.entity.ProductEntity
import com.example.apidemo.repository.entity.SellerEntity
import com.example.apidemo.support.RepositoryTest
import com.example.apidemo.support.TestEnvironment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.BeforeTest


@DataJpaTest
@TestEnvironment
class RepositoryTests (
    val productRepository: ProductRepository,
    val sellerRepository: SellerRepository
) {

    var seller1: SellerEntity? = null
    var seller2: SellerEntity? = null


    @BeforeTest
    fun setUp() {
        //seller 저장
        seller1 = sellerRepository.save(SellerReq(name = "판매자1", email = "seller1@naver.com", phone = "01040448500").toEntity())
        seller2 = sellerRepository.save(SellerReq(name = "판매자2", email = "seller2@naver.com", phone = "01040448500").toEntity())


    }

    @Test
    fun getProductTest() {

        //val seller1 = sellerRepository.save(SellerReq(name = "판매자1", email = "seller1@naver.com", phone = "01040448500").toEntity())
        //val seller2 = sellerRepository.save(SellerReq(name = "판매자2", email = "seller2@naver.com", phone = "01040448500").toEntity())

        val product1 = (ProductReq(name = "test1", price = 1000.0, category = "game", seller = seller1!!.toDto()).toEntity())
            .also {
                println(it.toString())
            }
        val product2 = (ProductReq(name = "test2", price = 2000.0, category = "vod", seller = seller2!!.toDto()).toEntity())
            .also {
                println(it.toString())
            }


        productRepository.saveAllAndFlush(listOf(product1, product2)).also {
            println(it.toString())
        }

        val entity = productRepository.findAll().onEach { println(it.seller.toString()) }

        val result = productRepository.findAllProductDTOs().also {
            println(it.toString())
        }

        // 테스트 검증
        assertEquals(2, result.size, "저장된 상품 개수는 2개여야 합니다.")
        assertEquals("test1", result[0].name, "첫 번째 상품의 이름은 'test1'이어야 합니다.")
        assertEquals(1000.0, result[0].price, "첫 번째 상품의 가격은 1000.0이어야 합니다.")
        //assertEquals(seller1!!.sellerId, result[0].seller.sellerId, "첫 번째 상품의 판매자 ID가 일치해야 합니다.")

        assertEquals("test2", result[1].name, "두 번째 상품의 이름은 'test2'이어야 합니다.")
        assertEquals(2000.0, result[1].price, "두 번째 상품의 가격은 2000.0이어야 합니다.")
       // assertEquals(seller2!!.sellerId, result[1].seller.sellerId, "두 번째 상품의 판매자 ID가 일치해야 합니다.")

    }

    @Test
    fun testDeleteSeller() {
        // 판매자 생성
        //val seller = SellerEntity(name = "판매자1", email = "seller1@naver.com", phone = "01040448500")

        // 상품 생성
        val product1 = ProductEntity(name = "스마트폰", price = 1000.0, category = "전자제품", seller = seller1!!)
        val product2 = ProductEntity(name = "태블릿", price = 2000.0, category = "전자제품", seller = seller1!!)

        // 연관관계 설정
        seller1!!.products = mutableSetOf(product1, product2)

        // 저장
        sellerRepository.saveAndFlush(seller1!!)

        // 저장된 상품 개수 확인
        assertEquals(2, productRepository.findAll().size)

        // 판매자 삭제
        sellerRepository.delete(seller1!!)

        // 삭제 후 상품 개수 확인 (자동 삭제되어야 함)
        assertEquals(0, productRepository.findAll().size)
    }

    @Test
    fun testGenericFindByName() {
        // 상품 생성
        val product1 = ProductEntity(name = "스마트폰", price = 1000.0, category = "전자제품", seller = seller1!!)
        val product2 = ProductEntity(name = "태블릿", price = 2000.0, category = "전자제품", seller = seller1!!)
        val product3 = ProductEntity(name = "노트북", price = 3000.0, category = "전자제품", seller = seller1!!)

        // 저장
        productRepository.saveAllAndFlush(listOf(product1, product2, product3))

        val allProducts = productRepository.findAll()

        // ID와 Name만 조회
        val basicProducts: List<BaseProductProjection> = productRepository.findByName("스마트폰", BaseProductProjection::class.java).toList()

        basicProducts.forEach {
            println("ID: ${it.getProductId()}, Name: ${it.getName()}")
        }

        // 모든 필드 조회 (Full Projection)
        val fullProducts: List<FullProductProjection> = productRepository.findByName("스마트폰", FullProductProjection::class.java).toList()
        fullProducts.forEach {
            println("ID: ${it.getProductId()}, Name: ${it.getName()}, Price: ${it.getPrice()}")
        }

    }


    @Test
    fun nPlus1ProblemTest() {

        // 상품 생성
        val product1 = ProductEntity(name = "스마트폰", price = 1000.0, category = "전자제품", seller = seller1!!)
        val product2 = ProductEntity(name = "태블릿", price = 2000.0, category = "전자제품", seller = seller1!!)
        val product3 = ProductEntity(name = "노트북", price = 3000.0, category = "전자제품", seller = seller1!!)

        productRepository.saveAll(listOf(product1, product2, product3))

        val products = productRepository.findAll().also {
            for(product in it) {
                println("상품 : ${product.name}")
                println("  - 판매자 : ${product.seller.name}")
            }

        }

        seller1!!.addAllProducts(products)
        sellerRepository.save(seller1!!)

        //val sellers = sellerRepository.findAll()
        val sellers = sellerRepository.findAll()

        for(seller in sellers) {
            println("판매자 : ${seller.name}")
            for(product in seller.products) {
                println("  - 상품 : ${product.name}, 가격 : ${product.price}")
            }
        }

    }
}