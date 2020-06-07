package io.ryuichi.composition

import io.ryuichi.App
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulOne
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulTwo
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
}
