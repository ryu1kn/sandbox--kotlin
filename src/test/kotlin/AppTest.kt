import io.ryuichi.CaseBasicCoroutine
import io.ryuichi.cancellation.CaseBasicCancellation
import io.ryuichi.cancellation.CaseFinallyCancellation
import io.ryuichi.cancellation.CaseManualCheckCancellation
import io.ryuichi.cancellation.CaseNotCooperativeCancellation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    private infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    @Test
    fun `App using coroutine`() {
        CaseBasicCoroutine().apply {
            run()
            getResult() equalsTo "Hello\nBeautiful\nWorld\n!"
        }
    }

    @Test
    fun `Cancel coroutine`() {
        CaseBasicCancellation().apply {
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
        CaseNotCooperativeCancellation().apply {
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
        CaseManualCheckCancellation().apply {
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

    @Test
    fun `Cancellation is just an exception, use finally to close resources`() {
        CaseFinallyCancellation().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |main: I'm tired of waiting!
                |job: I'm running finally
                |main: Now I can quit.""".trimMargin()
        }
    }
}
