package io.ryuichi.cancellation

import io.ryuichi.App
import kotlinx.coroutines.*

class CaseNotCooperativeCancellation : App() {
    override fun run() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += waitTime()
                }
            }
        }
        delay(waitTime(2.5))
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}
