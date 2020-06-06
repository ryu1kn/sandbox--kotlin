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
    private val printed = mutableListOf<String>()
    private val print = { s: String -> printed.add(s) }

    private val waitTime = 5L

    fun run() {
        runBlocking<Unit> {
            launch {
                launch {
                    delay(waitTime)
                    print("Beautiful")
                }
                coroutineScope {
                    delay(waitTime * 2)
                    print("World")
                }
                delay(waitTime)
                print("!")
            }
            print("Hello")
        }
    }

    fun getResult() = printed.joinToString(" ")
}
