import io.ryuichi.JobRunner
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    private infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    @Test
    fun `find who to greet`() = runBlocking<Unit> {
        JobRunner().apply {
            run()
            getResult() equalsTo "Hello World!"
        }
    }
}
