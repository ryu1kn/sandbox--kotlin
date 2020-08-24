package io.ryuichi

abstract class App {
    private val printed = mutableListOf<String>()

    protected fun waitTime(factor: Int = 1) = 10L * factor
    protected fun waitTime(factor: Double) = (waitTime().toDouble() * factor).toLong()

    protected open val println = { s: String -> printed.add(s) }

    abstract fun run()

    fun getResult() = printed.joinToString("\n")
}
