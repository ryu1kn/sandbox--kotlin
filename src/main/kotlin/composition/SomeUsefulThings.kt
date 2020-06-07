package io.ryuichi.composition

import kotlinx.coroutines.delay

object SomeUsefulThings {
    private val waitTime = 10L

    suspend fun doSomethingUsefulOne(): Int {
        delay(waitTime)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(waitTime)
        return 29
    }
}
