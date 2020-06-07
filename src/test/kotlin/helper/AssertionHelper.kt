package helper

import kotlin.test.assertEquals
import kotlin.test.assertTrue

interface AssertionHelper {
    infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)

    infix fun String.shouldContain(expected: String) =
        assertTrue(this.contains(expected), """Expected "$this" to contain "$expected"""")
}
