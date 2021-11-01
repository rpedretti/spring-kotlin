package com.testspring.rpedretti.springkotlin.services

import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import com.testspring.rpedretti.springkotlin.persistence.repositories.ProductTypeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DbProductTypeService(private val productTypeRepository: ProductTypeRepository) : ProductTypeService {
    override suspend fun getProductTypes(): Iterable<ProductType> = coroutineScope {
        val task = async {
            productTypeRepository.findAll()
        }
        val response = task.await()
        response
    }

    override suspend fun getProductType(id: Int): ProductType? = coroutineScope{
        val task = async {
            productTypeRepository.findByIdOrNull(id)
        }
        val response = task.await()
        response
    }
}