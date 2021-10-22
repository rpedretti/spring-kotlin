package com.testspring.rpedretti.springkotlin.controllers

import com.ninjasquad.springmockk.MockkBean
import com.testspring.rpedretti.springkotlin.services.SomeService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import java.math.BigInteger

@WebFluxTest
class FirstApiControllerTest(@Autowired val testClient: WebTestClient) {
    @MockkBean
    lateinit var someService: SomeService

    @Test
    fun `getHello should have correct response`() {
        coEvery { someService.getValue(any(), eq("1")) } returns "hello world 2"
        coEvery { someService.getValue(any(), eq("2")) } returns "hello world 4"

        testClient.get()
            .uri("/api/first/hello")
            .exchange()
            .expectStatus().isOk
            .expectBodyList<ResponseBody>()
            .hasSize(2)
            .isEqualTo<WebTestClient.ListBodySpec<ResponseBody>>(
                listOf(
                    ResponseBody("applied to: hello world 2", listOf(104, 108, 108, 32, 114, 108, 100, 32, 50)),
                    ResponseBody("applied to: hello world 4", listOf(104, 108, 108, 32, 114, 108, 100, 32, 52))
                )
            )

        coVerify(exactly = 2) { someService.getValue(any(), any()) }
    }

    @Test
    fun `postBla should have correct response`() {
        testClient.post()
            .uri("/api/first/bla")
            .bodyValue(RequestBodyParam("hello", listOf(1)))
            .exchange()
            .expectStatus().isOk
            .expectBody<ResponseBody2>()
            .isEqualTo(ResponseBody2(listOf(1), "hello"))
    }

    @Test
    fun `getFib should have correct response`() {
        coEvery { someService.fib(any()) } returns BigInteger.ONE
        testClient.get()
            .uri("/api/first/fib?t=5")
            .exchange()
            .expectStatus().isOk
            .expectBody<BigInteger>()
            .isEqualTo(BigInteger.ONE)

        verify { someService.fib(5) }
    }

    @Test
    fun `getWorld should have correct response`() {
        coEvery { someService.getValues(any(), any()) } returns listOf(1, 2, 3)
        testClient.get()
            .uri("/api/first/world?t=5")
            .exchange()
            .expectStatus().isOk
            .expectBody<List<Int>>()
            .isEqualTo(listOf(1, 2, 3))

        coVerify(exactly = 1) { someService.getValues(300, "5") }
        coVerify(exactly = 1) { someService.getValues(any(), any()) }
    }

    @Test
    fun `getWorld should return bad request on exception`() {
        coEvery { someService.getValues(any(), any()) } throws Exception("some message")
        testClient.get()
            .uri("/api/first/world?t=5")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody<ErrorMessage>()
            .isEqualTo(ErrorMessage("some message"))

        coVerify(exactly = 1) { someService.getValues(300, "5") }
        coVerify(exactly = 1) { someService.getValues(any(), any()) }
    }
}