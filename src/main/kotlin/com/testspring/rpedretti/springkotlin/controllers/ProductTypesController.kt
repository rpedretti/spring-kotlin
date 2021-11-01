package com.testspring.rpedretti.springkotlin.controllers

import com.testspring.rpedretti.springkotlin.persistence.models.ProductType
import com.testspring.rpedretti.springkotlin.services.ProductTypeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/producttypes")
class ProductTypesController(private val productTypesService: ProductTypeService) {
    @GetMapping("/", produces = ["application/json"])
    suspend fun getAll(): Iterable<ProductType> {
        return productTypesService.getProductTypes()
    }
}