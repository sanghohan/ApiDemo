package com.example.apidemo.repository.entity

import com.example.apidemo.dto.SellerDto
import jakarta.persistence.*

@Entity
@Table(name = "seller")
class SellerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    val sellerId: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "phone")
    var phone: String? = null,

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var products: MutableSet<ProductEntity> = mutableSetOf()

) {
    fun addProduct(product: ProductEntity) {
        products.add(product)   // products 리스트에 추가
        product.seller = this   // product의 seller 설정
    }

    fun addAllProducts(products: List<ProductEntity>) {
        products.forEach { addProduct(it) }
    }

    override fun toString(): String {
        return "id = $sellerId, name = $name, email = $email, phone = $phone"
    }

    fun toDto() = SellerDto(
        sellerId = sellerId,
        name = name,
        email = email,
        phone = phone
    )
}