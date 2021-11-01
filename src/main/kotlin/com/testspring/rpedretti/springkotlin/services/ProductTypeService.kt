package com.testspring.rpedretti.springkotlin.services

import com.testspring.rpedretti.springkotlin.persistence.models.ProductType

interface ProductTypeService {
    suspend fun getProductTypes(): Iterable<ProductType>
    suspend fun getProductType(id: Int): ProductType?
}