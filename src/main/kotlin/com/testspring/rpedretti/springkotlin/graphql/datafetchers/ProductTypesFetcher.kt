package com.testspring.rpedretti.springkotlin.graphql.datafetchers

import com.netflix.graphql.dgs.*
import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import com.testspring.rpedretti.springkotlin.services.ProductTypeService

@DgsComponent
class ProductTypesFetcher(private val productTypeService: ProductTypeService) {

    @DgsQuery
    suspend fun productTypes(): Iterable<ProductType> {
        return productTypeService.getProductTypes()
    }

    @DgsQuery
    suspend fun productType(@InputArgument productTypeId: Int): ProductType? {
        return productTypeService.getProductType(productTypeId)
    }
}