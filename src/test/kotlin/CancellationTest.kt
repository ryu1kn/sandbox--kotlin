import helper.AssertionHelper
import io.ryuichi.cancellation.*
import org.junit.jupiter.api.Test

class CancellationTest : AssertionHelper {
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

    @Test
    fun `Calling suspending function in cancelled coroutineScope is possible with NonCancellable context`() {
        CaseSuspendInCancelledCoroutine().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |main: I'm tired of waiting!
                |job: I'm running finally
                |job: And I've just delayed for 1 sec because I'm non-cancellable
                |main: Now I can quit.""".trimMargin()
        }
    }

    @Test
    fun `Automatically cancel coroutine if it times out`() {
        CaseTimeout().apply {
            run()
            getResult() equalsTo """
                |job: I'm sleeping 0 ...
                |job: I'm sleeping 1 ...
                |job: I'm sleeping 2 ...
                |main: timeout occurred
                |main: Now I can quit.""".trimMargin()
        }
    }
}
