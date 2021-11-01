package com.testspring.rpedretti.springkotlin.graphql.datafetchers

import com.netflix.graphql.dgs.*
import com.testspring.rpedretti.springkotlin.graphql.generated.DgsConstants.PRODUCT
import com.testspring.rpedretti.springkotlin.persistence.models.Product
import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import com.testspring.rpedretti.springkotlin.services.ProductService
import com.testspring.rpedretti.springkotlin.services.ProductTypeService

@DgsComponent
class ProductsDataFetcher(private val productService: ProductService, private val productTypeService: ProductTypeService) {

    @DgsQuery
    suspend fun products(): Iterable<Product> {
        val products = productService.getProducts()
        return products
    }

    @DgsQuery
    suspend fun product(@InputArgument productId: Int): Product? {
        return productService.getProduct(productId)
    }

    @DgsData(parentType = PRODUCT.TYPE_NAME, field = PRODUCT.ProductType)
    suspend fun productType(dfe: DgsDataFetchingEnvironment): ProductType {
        val parent = dfe.getSource<Product>()
        val productType = productTypeService.getProductType(parent.productTypeId)
        return productType!!
    }
}