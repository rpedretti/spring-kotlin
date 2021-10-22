package com.testspring.rpedretti.springkotlin.services

import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigInteger
import java.util.stream.Stream

internal class SomeServiceTest {

    private val service = LocalSomeService()

    companion object {
        @JvmStatic
        fun fibProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(1, 0.toBigInteger()),
                Arguments.of(2, 1.toBigInteger()),
                Arguments.of(3, 1.toBigInteger()),
                Arguments.of(4, 2.toBigInteger()),
                Arguments.of(5, 3.toBigInteger()),
                Arguments.of(6, 5.toBigInteger()),
            )
        }
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `getValue should have correct delay and value`() = runBlockingTest {
        val x = service.getValue(1000, "1")
        assertEquals(1000, currentTime)
        assertEquals("hello world", x)
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `getValues should fail with t = 2`() = runBlockingTest {
        val exception = assertThrows<IllegalArgumentException> {
            service.getValues(1000, "2")
        }

        assertEquals("t must not be 2", exception.message)
    }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun `getValues should success with t != 2`() = runBlockingTest {
        val values = service.getValues(1000, "1")
        assertEquals(4000, currentTime)
        assertEquals(listOf(1, 2, 3, 4), values)
    }

    @ParameterizedTest
    @MethodSource("fibProvider")
    fun `fib should calculate correctly`(input: Int, expected: BigInteger) {
        assertEquals(expected, service.fib(input))
    }
}