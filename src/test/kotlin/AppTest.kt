import io.ryuichi.LearnBasic
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    private infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    @Test
    fun `App using coroutine`() {
        LearnBasic().apply {
            run()
            getResult() equalsTo "Hello Beautiful World !"
        }
    }
}
