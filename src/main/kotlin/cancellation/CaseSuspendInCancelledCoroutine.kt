package io.ryuichi.cancellation

import io.ryuichi.App
import kotlinx.coroutines.*

class CaseSuspendInCancelledCoroutine : App() {
    override fun run() = runBlocking<Unit> {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(waitTime())
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(waitTime())
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(waitTime(2.5))
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}
