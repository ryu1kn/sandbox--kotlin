import helper.AssertionHelper
import io.ryuichi.context.CaseContextBasic
import org.junit.jupiter.api.Test

class ContextTest : AssertionHelper {
    @Test
    fun `Choose which context to run coroutine`() {
        CaseContextBasic().apply {
            run()
            getResult().apply {
                shouldContain("main runBlocking: Test worker")
                shouldContain("Unconfined: Test worker")
                shouldContain("Default: DefaultDispatcher-worker-1")
                shouldContain("newSingleThreadContext: MyOwnThread")
            }
        }
    }
}
