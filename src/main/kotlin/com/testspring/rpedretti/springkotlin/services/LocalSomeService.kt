package com.testspring.rpedretti.springkotlin.services

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class LocalSomeService : SomeService {
    private val logger = LoggerFactory.getLogger(javaClass)
    override suspend fun getValue(d: Long, t: String): String {
        logger.info("start get value $t")
        delay(d)
        logger.info("end get value $t")
        return "hello world"
    }

    override suspend fun getValues(d: Long, t: String): List<Int> {
        if (t == "2") throw IllegalArgumentException("t must not be 2")
        logger.info("start get values $t")
        val l = mutableListOf<Int>()
        (1..4).map {
            delay(d)
            l.add(it)
        }
        logger.info("end get values $t")

        return l
    }

    override fun fib(amount: Int): BigInteger {
        var x = BigInteger.ZERO
        var y = BigInteger.ONE
        for (i in 1 until amount) {
            x = y.also {
                y += x
            }
        }

        return x
    }
}
