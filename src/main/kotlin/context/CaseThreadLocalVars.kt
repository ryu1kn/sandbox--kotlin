package io.ryuichi.context

import io.ryuichi.App
import kotlinx.coroutines.*

class CaseThreadLocalVars : App() {
    private val threadLocal = ThreadLocal<String?>()

    override fun run() = runBlocking<Unit> {
        threadLocal.set("main")
        printThreadlocalValue("Pre-main")
        val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
            printThreadlocalValue("Launch start")
            yield()
            printThreadlocalValue("After yield")
        }
        job.join()
        printThreadlocalValue("Post-main")
    }

    private fun printThreadlocalValue(header: String) {
        println("$header, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
}
