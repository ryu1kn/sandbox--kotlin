package io.ryuichi

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    GlobalScope.launch {
        println(getWhoToGreet())
    }
    println("Hello,")
    delay(2000L)
}

suspend fun getWhoToGreet(): String {
    delay(1000L)
    return "World!"
}
