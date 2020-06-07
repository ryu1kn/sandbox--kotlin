package io.ryuichi

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LearnCancellation : App() {
    private val waitTime = 5L

    override fun run() = runBlocking<Unit> {
        val job = launch {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(waitTime)
            }
        }
        delay(waitTime * 3) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel()
        job.join()
        println("main: Now I can quit.")
    }
}
