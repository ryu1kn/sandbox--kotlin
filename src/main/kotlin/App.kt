package io.ryuichi

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    App().apply {
        run()
        println(getResult())
    }
}

class App {
    private val builder = StringBuilder()
    private val waitTime = 25L

    fun run() {
        runBlocking<Unit> {
            launch {
                launch {
                    delay(waitTime)
                    builder.append("!")
                }
                delay(waitTime)
                builder.append("World")
            }
            builder.append("Hello ")
        }
    }

    fun getResult() = builder.toString()
}
