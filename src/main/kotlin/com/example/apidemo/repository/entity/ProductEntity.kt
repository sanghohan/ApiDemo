package com.example.apidemo.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var productId: Long? = null, // 단일 기본 키

    @Version
    var version: Long? = 0,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "price")
    var price: Double? = null,

    @Column(name = "category")
    var category: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id") // --> 외래키를 직접 관리한다.
    var seller: SellerEntity = SellerEntity()

) //: Persistable<ProductPK> {
{


    override fun toString(): String {
        return "productId = $productId, name = $name, price = $price, category = $category"
    }

}
/*

@Embeddable
data class ProductPK(
    @Column(name = "product_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "seller_id")
    val sellerId: Long? = null
) : Serializable*/
