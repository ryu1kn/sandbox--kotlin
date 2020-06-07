import helper.AssertionHelper
import io.ryuichi.context.CaseContextBasic
import io.ryuichi.context.CaseMultiThreads
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

    @Test
    fun `Jump between threads`() {
        CaseMultiThreads().apply {
            run()
            getResult().apply {
                shouldContain("\\[Ctx1 @coroutine#\\d+\\] Started in ctx1".toRegex())
                shouldContain("\\[Ctx2 @coroutine#\\d+\\] Working in ctx2".toRegex())
                shouldContain("\\[Ctx1 @coroutine#\\d+\\] Back to ctx1".toRegex())
            }
        }
    }
}
