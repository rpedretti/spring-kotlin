package com.testspring.rpedretti.springkotlin.declarations

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import java.util.stream.Stream


internal class StringDeclarationsTest {
    companion object {
        @JvmStatic
        fun myStringExtTestProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", "applied to: "),
                Arguments.of("some text", "applied to: some text"),
                Arguments.of("いくつかのテキスト ", "applied to: いくつかのテキスト ")
            )
        }
    }

    @ParameterizedTest
    @MethodSource("myStringExtTestProvider")
    fun `myStringExt should map correctly`(input: String?, expected: String?) {
        assertEquals(expected, input.myStringExt())
    }
}