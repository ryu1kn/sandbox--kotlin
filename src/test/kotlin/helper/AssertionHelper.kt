package helper

import kotlin.test.assertEquals

interface AssertionHelper {
    infix fun <T> T.equalsTo(expected: T) = assertEquals(expected, this)
}
