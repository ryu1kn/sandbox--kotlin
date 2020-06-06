import io.ryuichi.getWhoToGreet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    private infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    @Test
    fun `find who to greet`() = runBlocking {
        getWhoToGreet() equalsTo "World!"
    }
}
