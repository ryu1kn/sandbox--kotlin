package io.ryuichi

abstract class App {
    private val printed = mutableListOf<String>()

    protected val print = { s: String -> printed.add(s) }

    abstract fun run()

    fun getResult() = printed.joinToString(" ")
}
