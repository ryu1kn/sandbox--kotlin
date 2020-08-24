import helper.AssertionHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import org.junit.jupiter.api.Test

class ChannelTest : AssertionHelper {
    @Test
    fun `Send multiple values`() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..3) channel.send(x * x)
        }

        val values = mutableListOf<Int>()
        repeat(3) { values.add(channel.receive()) }

        values equalsTo listOf(1, 4, 9)
    }

    @Test
    fun `Channel can be closed by the sender`() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..3) channel.send(x * x)
            channel.close()
        }

        val values = mutableListOf<Int>()
        for (x in channel) values.add(x)

        values equalsTo listOf(1, 4, 9)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Use a producer to build a channel that produce values`() = runBlocking {
        val channel = produce { for (x in 1..3) send(x * x) }

        val values = mutableListOf<Int>()
        channel.consumeEach { values.add(it) }

        values equalsTo listOf(1, 4, 9)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Use a pipeline pattern to process infinite number of values`() = runBlocking {
        fun CoroutineScope.produceNumbers() = produce {
            var x = 1
            while (true) send(x++)  // `send` suspends the caller execution if the buffer is full or doesn't exist
        }
        fun CoroutineScope.square(numbers: ReceiveChannel<Int>) = produce {
            for (x in numbers) send(x * x)
        }

        val numbers = produceNumbers()
        val squares = square(numbers)

        val values = mutableListOf<Int>()
        repeat(3) {
            values.add(squares.receive())
        }
        coroutineContext.cancelChildren()

        values equalsTo listOf(1, 4, 9)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Consecutively filter out values from channel, prime number`() = runBlocking {
        fun CoroutineScope.numbersFrom(start: Int) = produce {
            var x = start
            while (true) send(x++)
        }
        fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce {
            for (x in numbers) if (x % prime != 0) send(x)
        }

        val primes = mutableListOf<Int>()

        var cur = numbersFrom(2)
        repeat(10) {
            val prime = cur.receive()
            primes.add(prime)
            cur = filter(cur, prime)
        }
        coroutineContext.cancelChildren()

        primes equalsTo listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Use multiple coroutines to process values`() = runBlocking {
        fun CoroutineScope.produceNumbers() = produce {
            var x = 1
            while (true) {
                send(x++)
                delay(100)
            }
        }
        fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
            for (msg in channel) {
                println("Processor #$id received $msg")
            }
        }

        val producer = produceNumbers()
        repeat(5) { launchProcessor(it, producer) }
        delay(950)
        producer.cancel()
    }
}
