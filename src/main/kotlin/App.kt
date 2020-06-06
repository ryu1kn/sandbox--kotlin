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

    fun run() = runBlocking<Unit> {
        launch {
            delay(50L)
            builder.append("World!")
        }
        builder.append("Hello ")
    }

    fun getResult() = builder.toString()
}
