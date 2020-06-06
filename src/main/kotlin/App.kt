package io.ryuichi

import kotlinx.coroutines.coroutineScope
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
    private val print = { s: String -> builder.append(s) }

    private val waitTime = 25L

    fun run() {
        runBlocking<Unit> {
            launch {
                coroutineScope {
                    delay(waitTime)
                    print("World")
                }
                delay(waitTime)
                print("!")
            }
            print("Hello ")
        }
    }

    fun getResult() = builder.toString()
}
