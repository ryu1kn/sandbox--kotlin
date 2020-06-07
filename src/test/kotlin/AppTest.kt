import io.ryuichi.LearnBasic
import io.ryuichi.LearnCancellation
import io.ryuichi.LearnCancellationManualCheck
import io.ryuichi.LearnCancellationNoCooperative
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

    @Test
    fun `Unable to cancel non-cooperative (no-suspending function call) block`() {
        LearnCancellationNoCooperative().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |main: I'm tired of waiting!
                |job: I'm sleeping 3 ...
                |job: I'm sleeping 4 ...
                |main: Now I can quit.""".trimMargin()
        }
    }

    @Test
    fun `Turn compuation code cancellable by explicitly checking cancellation status`() {
        LearnCancellationManualCheck().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |job: I'm sleeping 3 ...
                |main: I'm tired of waiting!
                |main: Now I can quit.""".trimMargin()
        }
    }
}
