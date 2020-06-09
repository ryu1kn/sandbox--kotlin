import helper.AssertionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class MultithreadingTest : AssertionHelper {
    private val iteration = 100_000
    private fun countThreads(threadNames: List<String>) =
        threadNames.groupingBy { it.takeWhile { it != ' ' } }.eachCount()

    @Test
    fun `Use multiple threads - lock with synchronized`() = runBlocking {
        val threadNames = mutableListOf<String>()

        val time = measureTimeMillis {
            withContext(Dispatchers.Default) {
                (1..iteration).map {
                    launch {
                        synchronized(threadNames) {
                            threadNames.add(Thread.currentThread().name)
                        }
                    }
                }
            }
        }

        threadNames.size equalsTo iteration
        println(countThreads(threadNames))
        println("Time taken: $time")
    }

    @Test
    fun `Use multiple threads - lock with mutex`() = runBlocking {
        val mutex = Mutex()
        val threadNames = mutableListOf<String>()

        val time = measureTimeMillis {
            withContext(Dispatchers.Default) {
                (1..iteration).map {
                    mutex.withLock {
                        threadNames.add(Thread.currentThread().name)
                    }
                }
            }
        }

        threadNames.size equalsTo iteration
        println(countThreads(threadNames))
        println("Time taken: $time")
    }
}
