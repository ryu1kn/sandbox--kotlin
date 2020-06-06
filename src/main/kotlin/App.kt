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

    fun run() = runBlocking {
        launch {
            builder.append(getWhoToGreet())
        }
        builder.append("Hello ")
    }

    private suspend fun getWhoToGreet(): String {
        delay(50L)
        return "World!"
    }

    fun getResult() = builder.toString()
}
