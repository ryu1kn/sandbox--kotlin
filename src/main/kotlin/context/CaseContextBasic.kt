package io.ryuichi.context

import io.ryuichi.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

class CaseContextBasic : App() {
    override fun run() = runBlocking<Unit> {
        launch {
            println("main runBlocking: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {                // not confined -- will work with main thread
            println("Unconfined: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {                   // will get dispatched to DefaultDispatcher
            println("Default: ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("newSingleThreadContext: ${Thread.currentThread().name}")
        }
    }
}
