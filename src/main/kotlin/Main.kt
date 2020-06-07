package io.ryuichi

fun main() {
    LearnBasic().apply {
        run()
        println(getResult())
    }
    LearnCancellation().apply {
        run()
        println(getResult())
    }
}
