import helper.AssertionHelper
import io.ryuichi.context.CaseContextBasic
import io.ryuichi.context.CaseMultiThreads
import io.ryuichi.context.CaseThreadLocalVars
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
                shouldContain("\\[Ctx1 @coroutine#\\d+] Started in ctx1".toRegex())
                shouldContain("\\[Ctx2 @coroutine#\\d+] Working in ctx2".toRegex())
                shouldContain("\\[Ctx1 @coroutine#\\d+] Back to ctx1".toRegex())
            }
        }
    }

    @Test
    fun `Use thread-local variable`() {
        CaseThreadLocalVars().apply {
            run()
            getResult().apply {
                shouldContain("Pre-main, current thread: Thread\\[Test worker @coroutine#.*\\], thread local value: 'main'".toRegex())
                shouldContain("Launch start, current thread: Thread\\[DefaultDispatcher-worker-\\d+ @coroutine#.*\\], thread local value: 'launch'".toRegex())
                shouldContain("After yield, current thread: Thread\\[DefaultDispatcher-worker-\\d+ @coroutine#.*\\], thread local value: 'launch'".toRegex())
                shouldContain("Post-main, current thread: Thread\\[Test worker @coroutine#.*\\], thread local value: 'main'".toRegex())
            }
        }
    }
}
