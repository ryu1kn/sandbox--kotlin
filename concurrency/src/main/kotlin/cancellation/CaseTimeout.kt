package io.ryuichi.cancellation

import io.ryuichi.App
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class CaseTimeout : App() {
    override fun run() = runBlocking<Unit> {
        try {
            withTimeout(waitTime(2.5)) {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(waitTime())
                }
            }
        } catch (e: TimeoutCancellationException) {
            println("main: timeout occurred")
        }
        println("main: Now I can quit.")
    }
}
