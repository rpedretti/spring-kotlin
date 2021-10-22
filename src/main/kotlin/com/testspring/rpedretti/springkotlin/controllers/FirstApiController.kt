package com.testspring.rpedretti.springkotlin.controllers

import com.testspring.rpedretti.springkotlin.declarations.myStringExt
import com.testspring.rpedretti.springkotlin.services.SomeService
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

data class ResponseBody(val text: String, val numbers: List<Int>)
data class ResponseBody2(val numbers: List<Int>, val text: String)
data class RequestBodyParam(val text: String, val numbers: List<Int>)
data class ErrorMessage(val message: String?)

@RestController
@RequestMapping("/api/first")
class FirstApiController(val service: SomeService) {

    @GetMapping("/hello", produces = ["application/json"])
    suspend fun getHello(): List<ResponseBody> = coroutineScope {
        val t1 = async {
            val v = service.getValue(3000, "1")
            ResponseBody(v.myStringExt()!!, v.map { c -> c.code }.filter { c -> c % 2 == 0 })
        }
        val t2 = async {
            val v = service.getValue(2000, "2")
            ResponseBody(v.myStringExt()!!, v.map { c -> c.code }.filter { c -> c % 2 == 0 })
        }

        val (v1, v2) = awaitAll(t1, t2)
        listOf(v1, v2)
    }

    @PostMapping("/bla")
    suspend fun postBla(@RequestBody(required = true) request: RequestBodyParam): ResponseBody2 {
        return ResponseBody2(request.numbers, request.text)
    }

    @GetMapping("/fib")
    suspend fun getFib(@RequestParam(required = true, defaultValue = "1") t: Int): BigInteger {
        return service.fib(t)
    }

    @GetMapping("/world")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", content = [Content(
                    mediaType = "application/json", array = ArraySchema(
                        schema = Schema(implementation = Int::class)
                    )
                )]
            ),
            ApiResponse(
                responseCode = "400",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorMessage::class)
                )]
            ),
        ]
    )
    suspend fun getWorld(@RequestParam(required = false, defaultValue = "1") t: String): ResponseEntity<*> {
        return try {
            val values = service.getValues(300, t)
            ResponseEntity.ok(values.toList())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(ErrorMessage(e.message))
        }
    }
}