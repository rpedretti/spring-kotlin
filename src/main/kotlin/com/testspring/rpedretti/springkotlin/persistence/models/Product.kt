package com.testspring.rpedretti.springkotlin.persistence.models

import javax.persistence.*

@Entity
@Table
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var productId: Int?,
    @Column(nullable = false) val productName: String,
    @Column(nullable = false) val productTypeId: Int
) {
    constructor(
        productName: String,
        productTypeId: Int
    ) : this(null, productName, productTypeId)
}