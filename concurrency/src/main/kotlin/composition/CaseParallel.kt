package io.ryuichi.composition

import io.ryuichi.App
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulOne
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulTwo
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class CaseParallel : App() {
    override fun run() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }
}
