package io.ryuichi.composition

import io.ryuichi.App
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulOne
import io.ryuichi.composition.SomeUsefulThings.doSomethingUsefulTwo
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class CaseLazyAsynchronous : App() {
    override fun run() = runBlocking<Unit> {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }

            // Maybe doing some computations here

            one.start()
            two.start()

            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }
}
