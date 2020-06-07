package io.ryuichi.composition

import io.ryuichi.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class CaseSequential : App() {
    override fun run() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }

    private suspend fun doSomethingUsefulOne(): Int {
        delay(waitTime())
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(waitTime())
        return 29
    }
}
