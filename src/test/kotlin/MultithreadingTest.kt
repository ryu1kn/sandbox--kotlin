import helper.AssertionHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
                (1..iteration).forEach { _ ->
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
                (1..iteration).forEach { _ ->
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

    @ObsoleteCoroutinesApi
    @Test
    fun `Use multiple threads - Actor`() = runBlocking<Unit> {
        fun CoroutineScope.createThreadNameActor() = actor<ThreadNameMsg> {
            val threadNames = mutableListOf<String>()
            for (msg in channel) when (msg) {
                is CollectThreadNameMsg -> threadNames.add(Thread.currentThread().name)
                is GetCollectedThreadNames -> msg.result.complete(threadNames)
            }
        }

        val collector = createThreadNameActor()
        val time = measureTimeMillis {
            withContext(Dispatchers.Default) {
                (1..iteration).forEach { _ ->
                    launch {
                        collector.send(CollectThreadNameMsg)
                    }
                }
            }

            val result = CompletableDeferred<List<String>>()
            collector.send(GetCollectedThreadNames(result))

            val threadNames = result.await()
            threadNames.size equalsTo iteration
            println(countThreads(threadNames))
        }
        println("Time taken: $time")
        collector.close()
    }

    private interface ThreadNameMsg
    private object CollectThreadNameMsg : ThreadNameMsg
    private class GetCollectedThreadNames(val result: CompletableDeferred<List<String>>) : ThreadNameMsg
}
