package com.testspring.rpedretti.springkotlin.graphql.datafetchers

import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import com.ninjasquad.springmockk.MockkBean
import com.testspring.rpedretti.springkotlin.graphql.generated.client.*
import com.testspring.rpedretti.springkotlin.persistence.models.Product
import com.testspring.rpedretti.springkotlin.graphql.generated.types.Product as GProduct
import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import com.testspring.rpedretti.springkotlin.graphql.generated.types.ProductType as GProductType
import com.testspring.rpedretti.springkotlin.services.ProductService
import com.testspring.rpedretti.springkotlin.services.ProductTypeService
import io.mockk.coEvery
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [DgsAutoConfiguration::class, ProductsDataFetcher::class])
internal class ProductsDataFetcherTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockkBean
    private lateinit var productService: ProductService

    @MockkBean
    private lateinit var productTypeService: ProductTypeService

    @BeforeEach
    fun setup() {
        coEvery { productService.getProducts() } returns listOf(
            Product(1, "p1", 1),
            Product(2, "p2", 1),
            Product(3, "p3", 2),
        )
        coEvery { productService.getProduct(1) } returns Product(1, "p1", 1)
        coEvery { productService.getProduct(2) } returns Product(2, "p2", 1)
        coEvery { productService.getProduct(3) } returns Product(3, "p3", 2)

        coEvery { productTypeService.getProductTypes() } returns listOf(
            ProductType(1, "pt1"),
            ProductType(2, "pt2"),
            ProductType(3, "pt3"),
        )
        coEvery { productTypeService.getProductType(1) } returns ProductType(1, "pt1")
        coEvery { productTypeService.getProductType(2) } returns ProductType(2, "pt2")
    }

    @Test
    fun `products should return all products`() {
        val request = GraphQLQueryRequest(
            ProductsGraphQLQuery
                .Builder()
                .build(),
            ProductsProjectionRoot()
                .productId()
                .productName()
                .productType()
                    .productTypeId()
                    .productTypeName()
        )

        val products = dgsQueryExecutor.executeAndExtractJsonPathAsObject(request.serialize(), "data.products", object: TypeRef<List<GProduct>>() {})
        assertIterableEquals(
            listOf(
                GProduct(1, "p1", GProductType(1, "pt1")),
                GProduct(2, "p2", GProductType(1, "pt1")),
                GProduct(3, "p3", GProductType(2, "pt2")),
            ),
            products
        )
    }

    @Test
    fun `product should get correct product by ID`() {
        val request = GraphQLQueryRequest(
            ProductGraphQLQuery
                .Builder()
                .productId(1)
                .build(),
            ProductProjectionRoot()
                .productId()
                .productName()
                .productType()
                .productTypeId()
                .productTypeName()
        )

        val product = dgsQueryExecutor.executeAndExtractJsonPathAsObject(request.serialize(), "data.product", object: TypeRef<GProduct>() {})
        assertEquals(
            GProduct(1, "p1", GProductType(1, "pt1")),
            product
        )
    }
}