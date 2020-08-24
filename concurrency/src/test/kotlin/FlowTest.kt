import helper.AssertionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class FlowTest : AssertionHelper {
    private val values = listOf(1, 2, 3)

    @Test
    fun `Receive flow of values`() = runBlocking {
        val results = flow {
            for (i in 1..3) {
                delay(10L)
                emit(i)
            }
        }
        results.toList() equalsTo values
    }

    @Test
    fun `Other ways to create a flow from values`() = runBlocking {
        values.asFlow().toList() equalsTo values
        flowOf(*values.toTypedArray()).toList() equalsTo values
    }

    @Test
    fun `Transform values in a flow`() = runBlocking {
        flowOf(*values.toTypedArray()).map {it * 2}.toList() equalsTo listOf(2, 4, 6)
    }

    @Test
    fun `Filter values in a flow`() = runBlocking {
        flowOf(*values.toTypedArray()).filter { it % 2 == 0}.toList() equalsTo listOf(2)
    }

    @Test
    fun `"transform" can do more complex transformation in a flow`() = runBlocking {
        val results = flowOf(*values.toTypedArray()).transform {
            emit("For $it")
            emit((it * it).toString())
        }
        results.toList() equalsTo listOf(
            "For 1", "1",
            "For 2", "4",
            "For 3", "9"
        )
    }

    @Test
    fun `limit the size of results`() = runBlocking {
        flowOf(*values.toTypedArray()).take(2).toList() equalsTo listOf(1, 2)
    }

    @Test
    fun `terminal flow operator`() = runBlocking {
        flowOf(*values.toTypedArray()).fold(0, {x, y -> x + y}) equalsTo 6
    }

    @Test
    fun `Change the context for flow to run`() = runBlocking {
        val results = flowOf(*values.toTypedArray())
        results.flowOn(Dispatchers.Default).toList() equalsTo values
    }

    @Test
    fun `Can run flow part concurrently`() = runBlocking {
        measureTimeMillis {
            flow {
                for (i in 1..3) {
                    delay(50)
                    emit(i)
                }
            }.buffer().map { delay(100); it }.toList() equalsTo values
        } shouldBeLessThan 450
    }
}
