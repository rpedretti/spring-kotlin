package com.testspring.rpedretti.springkotlin.services

import java.math.BigInteger

interface SomeService {
    suspend fun getValue(d: Long, t: String): String
    suspend fun getValues(d: Long, t: String): List<Int>
    fun fib(amount: Int): BigInteger
}