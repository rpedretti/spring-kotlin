package com.testspring.rpedretti.springkotlin.declarations

fun String?.myStringExt(): String? = when (this) {
    is String -> "applied to: $this"
    else -> null
}