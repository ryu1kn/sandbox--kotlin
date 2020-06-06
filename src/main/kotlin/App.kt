package io.ryuichi

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    JobRunner().apply {
        run()
        println(getResult())
    }
}

class JobRunner {
    private val builder = StringBuilder()

    suspend fun run() = runBlocking {
        GlobalScope.launch {
            builder.append(getWhoToGreet())
        }
        builder.append("Hello ")
        delay(2000L)
    }

    private suspend fun getWhoToGreet(): String {
        delay(1000L)
        return "World!"
    }

    fun getResult() = builder.toString()
}
