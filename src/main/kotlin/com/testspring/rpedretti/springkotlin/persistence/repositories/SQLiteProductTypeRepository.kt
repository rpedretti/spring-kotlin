package com.testspring.rpedretti.springkotlin.persistence.repositories

import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductTypeRepository : CrudRepository<ProductType, Int> {
}