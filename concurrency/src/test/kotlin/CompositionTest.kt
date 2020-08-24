import helper.AssertionHelper
import io.ryuichi.composition.CaseAsyncFailurePropagation
import io.ryuichi.composition.CaseLazyAsynchronous
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

    @Test
    fun `Lazily start asynchronous operations`() {
        CaseLazyAsynchronous().apply {
            run()
            expect("The answer is 42", { getResult().lines().first() })
            expect("Completed in ", { getResult().lines()[1].takeWhile { ! it.isDigit() } })
        }
    }

    @Test
    fun `Failed coroutine causes other coroutines in the same scope get cancelled`() {
        CaseAsyncFailurePropagation().apply {
            run()
            getResult() equalsTo """
                Second child throws an exception
                First child was cancelled
                Computation failed with ArithmeticException""".trimIndent()
        }
    }
}
