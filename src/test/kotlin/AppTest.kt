import io.ryuichi.LearnBasic
import io.ryuichi.LearnCancellation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    private infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    @Test
    fun `App using coroutine`() {
        LearnBasic().apply {
            run()
            getResult() equalsTo "Hello\nBeautiful\nWorld\n!"
        }
    }

    @Test
    fun `Cancel coroutine`() {
        LearnCancellation().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |main: I'm tired of waiting!
                |main: Now I can quit.""".trimMargin()
        }
    }
}
