import helper.AssertionHelper
import io.ryuichi.composition.CaseParallel
import io.ryuichi.composition.CaseSequential
import org.junit.jupiter.api.Test
import kotlin.test.expect

class CompositionTest : AssertionHelper {
    @Test
    fun `by default, coroutines are invoked sequentially inside coroutineScope`() {
        CaseSequential().apply {
            run()
            expect("The answer is 42", { getResult().lines().first() })
            expect("Completed in ", { getResult().lines()[1].takeWhile { ! it.isDigit() } })
        }
    }

    @Test
    fun `Start multiple coroutines at the same time with "async"`() {
        CaseParallel().apply {
            run()
            expect("The answer is 42", { getResult().lines().first() })
            expect("Completed in ", { getResult().lines()[1].takeWhile { ! it.isDigit() } })
        }
    }
}
