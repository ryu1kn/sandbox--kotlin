package io.ryuichi

import io.ryuichi.cancellation.CaseBasicCancellation

fun main() {
    CaseBasicCoroutine().apply {
        run()
        println(getResult())
    }
    CaseBasicCancellation().apply {
        run()
        println(getResult())
    }
}
