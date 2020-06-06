import io.ryuichi.getWhoToGreet
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun `find who to greet`() = runBlocking {
        assertEquals(getWhoToGreet(), "World!")
    }
}
