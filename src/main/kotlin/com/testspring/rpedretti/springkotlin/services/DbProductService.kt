package com.testspring.rpedretti.springkotlin.services

import com.testspring.rpedretti.springkotlin.persistence.models.Product
import com.testspring.rpedretti.springkotlin.persistence.repositories.ProductsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DbProductService(private val productsRepository: ProductsRepository) : ProductService {
    override suspend fun getProducts(): Iterable<Product> = coroutineScope {
        val task = async {
            productsRepository.findAll()
        }
        val response = task.await()
        response
    }

    override suspend fun getProduct(id: Int): Product? = coroutineScope {
        val task = async {
            productsRepository.findByIdOrNull(id)
        }
        val response = task.await()
        response
    }

    override suspend fun add(productName: String, productTYpeId: Int): Product = coroutineScope {
        val domainProduct = Product(productName, productTYpeId)
        val task = async {
            productsRepository.save(domainProduct)
        }

        val response = task.await()
        response
    }
}