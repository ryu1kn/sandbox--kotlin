package io.ryuichi

import kotlinx.coroutines.*

class CaseBasicCoroutine : App() {
    override fun run() {
        // Block the thread until all the operations inside finishes
        runBlocking<Unit> {
            // Creates a new coroutine scope that lives in the current scope
            launch {
                // Launch a suspending operation extracted as a function
                // marked with `suspend` keyword
                launch(block = praise)

                // suspend the execution of following block
                coroutineScope {
                    delay(waitTime(2))
                    println("World")
                }

                delay(waitTime())
                println("!")
            }
            println("Hello")
        }
    }

    private val praise: suspend CoroutineScope.() -> Unit = {
        delay(waitTime())
        println("Beautiful")
    }
}
