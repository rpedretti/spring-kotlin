package com.testspring.rpedretti.springkotlin.graphql.mutators

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.testspring.rpedretti.springkotlin.persistence.models.Product
import com.testspring.rpedretti.springkotlin.services.ProductService

@DgsComponent
class ProductsMutator(private val productService: ProductService) {

    @DgsMutation
    suspend fun addProduct(productName: String, productTypeId: Int): Product {
        return productService.add(productName, productTypeId)
    }
}