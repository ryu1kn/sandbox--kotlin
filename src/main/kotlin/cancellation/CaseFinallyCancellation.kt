package io.ryuichi.cancellation

import io.ryuichi.App
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CaseFinallyCancellation : App() {
    override fun run() = runBlocking<Unit> {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(waitTime())
                }
            } finally {
                println("job: I'm running finally")
            }
        }
        delay(waitTime(2.5))
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}
