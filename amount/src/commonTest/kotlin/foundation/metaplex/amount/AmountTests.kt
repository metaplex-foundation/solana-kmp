package foundation.metaplex.amount

import com.ionspin.kotlin.bignum.integer.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertTrue


/**
 * An amount of US dollars represented using the lowest possible unit — i.e. cents.
 * @category Utils — Amounts
 */
typealias UsdAmount = Amount<String, Int>

/**
 * Creates a UsdAmount from the provided decimal value in USD.
 * @category Utils — Amounts
 */
fun usd(usd: Int): UsdAmount {
    return usd(usd.toDouble())
}
fun usd(usd: Double): UsdAmount {
    return createAmountFromDecimals(usd, "USD", 2)
}

class AmountTests {
    @Test
    fun testCreateAmount() {
        val usdAmount = createAmount(1500, "USD", 2)
        val gbpAmount = createAmount(4200, "GBP", 2)

        assertEquals("1500", usdAmount.basisPoints.toString())
        assertEquals("USD", usdAmount.identifier)
        assertEquals(2, usdAmount.decimals)

        assertEquals("4200", gbpAmount.basisPoints.toString())
        assertEquals("GBP", gbpAmount.identifier)
        assertEquals(2, gbpAmount.decimals)
    }
    @Test
    fun testFormatting() {
        val percentAmount = createAmount(1234, "%", 2)
        val usdAmount = createAmount(1536, "USD", 2)
        val gbpAmount = createAmount(4210, "GBP", 2)
        val solAmount = createAmount(2_500_000_000, "SOL", 9)
        val solAmountLeadingZeroDecimal =
            createAmount(2_005_000_000, "SOL", 9)

        assertEquals("12.34", amountToString(percentAmount))
        assertEquals("12.34%", displayAmount(percentAmount))

        assertEquals("15.36", amountToString(usdAmount))
        assertEquals("USD 15.36", displayAmount(usdAmount))

        assertEquals("42.10", amountToString(gbpAmount))
        assertEquals("GBP 42.10", displayAmount(gbpAmount))

        assertEquals("2.500000000", amountToString(solAmount))
        assertEquals("2.50", amountToString(solAmount, 2))
        assertEquals("SOL 2.500000000", displayAmount(solAmount))
        assertEquals("SOL 2.50", displayAmount(solAmount, 2))

        assertEquals("2.005000000",
            amountToString(solAmountLeadingZeroDecimal)
        )
        assertEquals("SOL 2.005000000",
            displayAmount(solAmountLeadingZeroDecimal)
        )
    }

    @Test
    fun testCurrencyHelpers() {
        amountEquals(usd(15.36), "USD 15.36")
        amountEquals(usd(15.36), "USD 15.36")
        amountEquals(createAmount(1536, "USD", 2), "USD 15.36")
        amountEquals(sol(2.5), "SOL 2.500000000")
        amountEquals(lamports(2_500_000_000), "SOL 2.500000000")
        amountEquals(createAmount(2_500_000_000, "SOL", 9), "SOL 2.500000000")
        amountEquals(createAmount(BigInteger(2_500_000_000), "SOL", 9), "SOL 2.500000000")
    }

    @Test
    fun testTokenAmounts() {
        amountEquals(tokenAmount(1, null, null), "1 Token")
        amountEquals(tokenAmount(1, null, 5), "1.00000 Token")
        amountEquals(tokenAmount(1.5, null, 2), "1.50 Tokens")
        amountEquals(tokenAmount(4.5, "DGEN", null), "DGEN 4")
        amountEquals(tokenAmount(4.5, "DGEN", 2), "DGEN 4.50")
        amountEquals(tokenAmount(6.2587, "DGEN", 9), "DGEN 6.258700000")
    }

    @Test
    fun testAddAndSubtractAmounts() {
        val a = sol(1.5)
        val b = lamports(4200000000) // 4.2 SOL

        amountEquals(addAmounts(a, b), "SOL 5.700000000")
        amountEquals(addAmounts(b, a), "SOL 5.700000000")
        amountEquals(addAmounts(a, sol(1)), "SOL 2.500000000")

        amountEquals(subtractAmounts(a, b), "SOL -2.700000000")
        amountEquals(subtractAmounts(b, a), "SOL 2.700000000")
        amountEquals(
            subtractAmounts(
                a,
                sol(1)
            ), "SOL 0.500000000")
    }

    @Test
    fun testCurrencyMismatchError() {
        val error = assertFailsWith<AmountErrors.AmountMismatchError> {
            addAmounts(sol(1), usd(1))
        }

        assertTrue(error is AmountErrors.AmountMismatchError)
        assertEquals("SOL", error.left.identifier)
        assertEquals("USD", error.right.identifier)
        assertEquals("add", error.operation)
    }

    @Test
    fun testMultiplyAndDivideAmounts() {
        amountEquals(
            multiplyAmount(
                sol(1.5),
                3
            ), "SOL 4.500000000")
        amountEquals(
            multiplyAmount(
                sol(1.5),
                3.78
            ), "SOL 5.670000000")
        amountEquals(
            multiplyAmount(
                sol(1.5),
                -1
            ), "SOL -1.500000000")
        amountEquals(
            multiplyAmount(
                sol(1.5),
                BigInteger(3)
            ), "SOL 4.500000000")

        amountEquals(
            divideAmount(
                sol(1.5),
                3
            ), "SOL 0.500000000")
        amountEquals(
            divideAmount(
                sol(1.5),
                9
            ), "SOL 0.166666666")
        amountEquals(
            divideAmount(
                sol(1.5),
                -1
            ), "SOL -1.500000000")
        amountEquals(
            divideAmount(
                sol(1.5),
                BigInteger(9)
            ), "SOL 0.166666666")
    }

    @Test
    fun testComparisonOperations() {
        val a = sol(1.5)
        val b = lamports(4200000000) // 4.2 SOL

        assertFalse(isEqualToAmount(a, b))
        assertTrue(
            isEqualToAmount(
                a,
                sol(1.5)
            )
        )

        assertTrue(isLessThanAmount(a, b))
        assertFalse(isLessThanAmount(b, a))
        assertFalse(
            isLessThanAmount(
                a,
                sol(1.5)
            )
        )
        assertTrue(isLessThanOrEqualToAmount(a, b))
        assertTrue(
            isLessThanOrEqualToAmount(
                a,
                sol(1.5)
            )
        )

        assertFalse(isGreaterThanAmount(a, b))
        assertTrue(isGreaterThanAmount(b, a))
        assertFalse(
            isGreaterThanAmount(
                a,
                sol(1.5)
            )
        )
        assertFalse(isGreaterThanOrEqualToAmount(a, b))
        assertTrue(
            isGreaterThanOrEqualToAmount(
                a,
                sol(1.5)
            )
        )

        assertTrue(isPositiveAmount(a))
        assertFalse(isNegativeAmount(a))
        assertFalse(isZeroAmount(a))

        assertTrue(isPositiveAmount(sol(0)))
        assertFalse(isNegativeAmount(sol(0)))
        assertTrue(isZeroAmount(sol(0)))

        assertFalse(isPositiveAmount(sol(-1)))
        assertTrue(isNegativeAmount(sol(-1)))
        assertFalse(isZeroAmount(sol(-1)))
    }

    @Test
    fun testComparisonWithTolerance() {
        assertFalse(
            isEqualToAmount(
                sol(1.5),
                sol(1.6)
            )
        )
        assertFalse(
            isEqualToAmount(
                sol(1.5),
                sol(1.6),
                sol(0.01)
            )
        )
        assertTrue(
            isEqualToAmount(
                sol(1.5),
                sol(1.6),
                sol(0.1)
            )
        )
        assertTrue(
            isEqualToAmount(
                sol(1.5),
                sol(1.6),
                sol(0.2)
            )
        )
    }

    @Test
    fun testInstanceReturn() {
        val a = sol(1.5)
        val b = lamports(4200000000) // 4.2 SOL

        assertNotSame(a, addAmounts(a, b))
        assertNotSame(b, addAmounts(a, b))
        assertNotSame(a, subtractAmounts(a, b))
        assertNotSame(b, subtractAmounts(a, b))
        assertNotSame(a, multiplyAmount(a, 3))
        assertNotSame(a, divideAmount(a, 3))
    }

    @Test
    fun testCreatePercentAmounts() {
        amountEquals(percentAmount(5.5, 2), "5.50%")
        amountEquals(percentAmount(5.5, 2), "5.50%")
        amountEquals(percentAmount(5.5, 4), "5.5000%")
        amountEquals(percentAmount(5.12345, 4), "5.1234%")
        amountEquals(percentAmount(5.12345, 0), "5%")
        amountEquals(percentAmount(100.0, 2), "100.00%")
        amountEquals(percentAmount(250.0, 2), "250.00%")
    }

    private fun amountEquals(amount: Amount<*, *>, expected: String) {
        val formattedAmount = displayAmount(amount)
        assertEquals(expected, formattedAmount)
    }
}