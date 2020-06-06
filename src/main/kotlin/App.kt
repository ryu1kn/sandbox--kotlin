package io.ryuichi

import kotlinx.coroutines.*

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
                launch(block = praise)
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

    private val praise: suspend CoroutineScope.() -> Unit = {
        delay(waitTime)
        print("Beautiful")
    }

    fun getResult() = printed.joinToString(" ")
}
