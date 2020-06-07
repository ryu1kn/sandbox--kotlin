import helper.AssertionHelper
import io.ryuichi.CaseBasicCoroutine
import org.junit.jupiter.api.Test

class AppTest : AssertionHelper {

    @Test
    fun `App using coroutine`() {
        CaseBasicCoroutine().apply {
            run()
            getResult() equalsTo "Hello\nBeautiful\nWorld\n!"
        }
    }
}
