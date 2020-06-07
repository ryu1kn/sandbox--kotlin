package io.ryuichi.context

import io.ryuichi.App
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CaseMultiThreads : App() {
    override fun run() = runBlocking<Unit> {
        // Leveraging `use` to automatically close the resource after use
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {         // Specifying the context to run coroutine
                    println("Started in ctx1")
                    withContext(ctx2) {     // Specifying the context to run coroutine
                        println("Working in ctx2")
                    }
                    println("Back to ctx1")
                }
            }
        }
    }

    override val println = { s: String -> super.println("[${Thread.currentThread().name}] $s") }
}
