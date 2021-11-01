package com.testspring.rpedretti.springkotlin.persistence.models

import javax.persistence.*

@Entity
@Table
class ProductType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val productTypeId: Int,
    @Column(nullable = false) val productTypeName: String,
)