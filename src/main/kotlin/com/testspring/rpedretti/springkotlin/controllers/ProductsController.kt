package com.testspring.rpedretti.springkotlin.controllers

import com.testspring.rpedretti.springkotlin.persistence.models.Product
import com.testspring.rpedretti.springkotlin.services.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductsController(private val productService: ProductService) {

    @GetMapping("/", produces = ["application/json"])
    suspend fun getAll(): Iterable<Product> {
        return productService.getProducts()
    }
}