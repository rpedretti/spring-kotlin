package com.testspring.rpedretti.springkotlin.services

import com.testspring.rpedretti.springkotlin.persistence.models.Product

interface ProductService {
    suspend fun getProducts(): Iterable<Product>
    suspend fun getProduct(id: Int): Product?
    suspend fun add(productName: String, productTYpeId: Int): Product
}