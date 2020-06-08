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

    @Test
    fun `Accessing mutatable variable from multiple threads cause a bug`() {
        CaseRaceCondition().apply {
            run()
            result.toInt() shouldBeLessThan 100000
        }
    }
}
