import helper.AssertionHelper
import org.junit.jupiter.api.Test

class RunnableTest : AssertionHelper {
    @Test
    fun `Run a task`() {
        CaseRunnable().apply {
            run()
            result shouldContain """
                Hello Test worker
                Hello Thread-\d+
                Done!
            """.trimIndent().toRegex()
        }
    }
}
