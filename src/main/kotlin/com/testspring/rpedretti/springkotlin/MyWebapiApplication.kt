package com.testspring.rpedretti.springkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyWebapiApplication

fun main(args: Array<String>) {
    runApplication<MyWebapiApplication>(*args)
}
