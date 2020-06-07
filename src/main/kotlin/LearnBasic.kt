package io.ryuichi

import kotlinx.coroutines.*

class LearnBasic : App() {
    private val waitTime = 5L

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
}
