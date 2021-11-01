package com.testspring.rpedretti.springkotlin.persistence.repositories

import com.testspring.rpedretti.springkotlin.persistence.models.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.*

@Repository
interface ProductsRepository : CrudRepository<Product, Int> {
}