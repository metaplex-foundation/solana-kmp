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
typealias UsdAmount = foundation.metaplex.amount.Amount<String, Int>

/**
 * Creates a UsdAmount from the provided decimal value in USD.
 * @category Utils — Amounts
 */
fun usd(usd: Int): UsdAmount {
    return usd(usd.toDouble())
}
fun usd(usd: Double): UsdAmount {
    return foundation.metaplex.amount.createAmountFromDecimals(usd, "USD", 2)
}

class AmountTests {
    @Test
    fun testCreateAmount() {
        val usdAmount = foundation.metaplex.amount.createAmount(1500, "USD", 2)
        val gbpAmount = foundation.metaplex.amount.createAmount(4200, "GBP", 2)

        assertEquals("1500", usdAmount.basisPoints.toString())
        assertEquals("USD", usdAmount.identifier)
        assertEquals(2, usdAmount.decimals)

        assertEquals("4200", gbpAmount.basisPoints.toString())
        assertEquals("GBP", gbpAmount.identifier)
        assertEquals(2, gbpAmount.decimals)
    }
    @Test
    fun testFormatting() {
        val percentAmount = foundation.metaplex.amount.createAmount(1234, "%", 2)
        val usdAmount = foundation.metaplex.amount.createAmount(1536, "USD", 2)
        val gbpAmount = foundation.metaplex.amount.createAmount(4210, "GBP", 2)
        val solAmount = foundation.metaplex.amount.createAmount(2_500_000_000, "SOL", 9)
        val solAmountLeadingZeroDecimal =
            foundation.metaplex.amount.createAmount(2_005_000_000, "SOL", 9)

        assertEquals("12.34", foundation.metaplex.amount.amountToString(percentAmount))
        assertEquals("12.34%", foundation.metaplex.amount.displayAmount(percentAmount))

        assertEquals("15.36", foundation.metaplex.amount.amountToString(usdAmount))
        assertEquals("USD 15.36", foundation.metaplex.amount.displayAmount(usdAmount))

        assertEquals("42.10", foundation.metaplex.amount.amountToString(gbpAmount))
        assertEquals("GBP 42.10", foundation.metaplex.amount.displayAmount(gbpAmount))

        assertEquals("2.500000000", foundation.metaplex.amount.amountToString(solAmount))
        assertEquals("2.50", foundation.metaplex.amount.amountToString(solAmount, 2))
        assertEquals("SOL 2.500000000", foundation.metaplex.amount.displayAmount(solAmount))
        assertEquals("SOL 2.50", foundation.metaplex.amount.displayAmount(solAmount, 2))

        assertEquals("2.005000000",
            foundation.metaplex.amount.amountToString(solAmountLeadingZeroDecimal)
        )
        assertEquals("SOL 2.005000000",
            foundation.metaplex.amount.displayAmount(solAmountLeadingZeroDecimal)
        )
    }

    @Test
    fun testCurrencyHelpers() {
        amountEquals(usd(15.36), "USD 15.36")
        amountEquals(usd(15.36), "USD 15.36")
        amountEquals(foundation.metaplex.amount.createAmount(1536, "USD", 2), "USD 15.36")
        amountEquals(foundation.metaplex.amount.sol(2.5), "SOL 2.500000000")
        amountEquals(foundation.metaplex.amount.lamports(2_500_000_000), "SOL 2.500000000")
        amountEquals(foundation.metaplex.amount.createAmount(2_500_000_000, "SOL", 9), "SOL 2.500000000")
        amountEquals(foundation.metaplex.amount.createAmount(BigInteger(2_500_000_000), "SOL", 9), "SOL 2.500000000")
    }

    @Test
    fun testTokenAmounts() {
        amountEquals(foundation.metaplex.amount.tokenAmount(1, null, null), "1 Token")
        amountEquals(foundation.metaplex.amount.tokenAmount(1, null, 5), "1.00000 Token")
        amountEquals(foundation.metaplex.amount.tokenAmount(1.5, null, 2), "1.50 Tokens")
        amountEquals(foundation.metaplex.amount.tokenAmount(4.5, "DGEN", null), "DGEN 4")
        amountEquals(foundation.metaplex.amount.tokenAmount(4.5, "DGEN", 2), "DGEN 4.50")
        amountEquals(foundation.metaplex.amount.tokenAmount(6.2587, "DGEN", 9), "DGEN 6.258700000")
    }

    @Test
    fun testAddAndSubtractAmounts() {
        val a = foundation.metaplex.amount.sol(1.5)
        val b = foundation.metaplex.amount.lamports(4200000000) // 4.2 SOL

        amountEquals(foundation.metaplex.amount.addAmounts(a, b), "SOL 5.700000000")
        amountEquals(foundation.metaplex.amount.addAmounts(b, a), "SOL 5.700000000")
        amountEquals(foundation.metaplex.amount.addAmounts(a, foundation.metaplex.amount.sol(1)), "SOL 2.500000000")

        amountEquals(foundation.metaplex.amount.subtractAmounts(a, b), "SOL -2.700000000")
        amountEquals(foundation.metaplex.amount.subtractAmounts(b, a), "SOL 2.700000000")
        amountEquals(
            foundation.metaplex.amount.subtractAmounts(
                a,
                foundation.metaplex.amount.sol(1)
            ), "SOL 0.500000000")
    }

    @Test
    fun testCurrencyMismatchError() {
        val error = assertFailsWith<AmountErrors.AmountMismatchError> {
            foundation.metaplex.amount.addAmounts(foundation.metaplex.amount.sol(1), usd(1))
        }

        assertTrue(error is AmountErrors.AmountMismatchError)
        assertEquals("SOL", error.left.identifier)
        assertEquals("USD", error.right.identifier)
        assertEquals("add", error.operation)
    }

    @Test
    fun testMultiplyAndDivideAmounts() {
        amountEquals(
            foundation.metaplex.amount.multiplyAmount(
                foundation.metaplex.amount.sol(1.5),
                3
            ), "SOL 4.500000000")
        amountEquals(
            foundation.metaplex.amount.multiplyAmount(
                foundation.metaplex.amount.sol(1.5),
                3.78
            ), "SOL 5.670000000")
        amountEquals(
            foundation.metaplex.amount.multiplyAmount(
                foundation.metaplex.amount.sol(1.5),
                -1
            ), "SOL -1.500000000")
        amountEquals(
            foundation.metaplex.amount.multiplyAmount(
                foundation.metaplex.amount.sol(1.5),
                BigInteger(3)
            ), "SOL 4.500000000")

        amountEquals(
            foundation.metaplex.amount.divideAmount(
                foundation.metaplex.amount.sol(1.5),
                3
            ), "SOL 0.500000000")
        amountEquals(
            foundation.metaplex.amount.divideAmount(
                foundation.metaplex.amount.sol(1.5),
                9
            ), "SOL 0.166666666")
        amountEquals(
            foundation.metaplex.amount.divideAmount(
                foundation.metaplex.amount.sol(1.5),
                -1
            ), "SOL -1.500000000")
        amountEquals(
            foundation.metaplex.amount.divideAmount(
                foundation.metaplex.amount.sol(1.5),
                BigInteger(9)
            ), "SOL 0.166666666")
    }

    @Test
    fun testComparisonOperations() {
        val a = foundation.metaplex.amount.sol(1.5)
        val b = foundation.metaplex.amount.lamports(4200000000) // 4.2 SOL

        assertFalse(foundation.metaplex.amount.isEqualToAmount(a, b))
        assertTrue(
            foundation.metaplex.amount.isEqualToAmount(
                a,
                foundation.metaplex.amount.sol(1.5)
            )
        )

        assertTrue(foundation.metaplex.amount.isLessThanAmount(a, b))
        assertFalse(foundation.metaplex.amount.isLessThanAmount(b, a))
        assertFalse(
            foundation.metaplex.amount.isLessThanAmount(
                a,
                foundation.metaplex.amount.sol(1.5)
            )
        )
        assertTrue(foundation.metaplex.amount.isLessThanOrEqualToAmount(a, b))
        assertTrue(
            foundation.metaplex.amount.isLessThanOrEqualToAmount(
                a,
                foundation.metaplex.amount.sol(1.5)
            )
        )

        assertFalse(foundation.metaplex.amount.isGreaterThanAmount(a, b))
        assertTrue(foundation.metaplex.amount.isGreaterThanAmount(b, a))
        assertFalse(
            foundation.metaplex.amount.isGreaterThanAmount(
                a,
                foundation.metaplex.amount.sol(1.5)
            )
        )
        assertFalse(foundation.metaplex.amount.isGreaterThanOrEqualToAmount(a, b))
        assertTrue(
            foundation.metaplex.amount.isGreaterThanOrEqualToAmount(
                a,
                foundation.metaplex.amount.sol(1.5)
            )
        )

        assertTrue(foundation.metaplex.amount.isPositiveAmount(a))
        assertFalse(foundation.metaplex.amount.isNegativeAmount(a))
        assertFalse(foundation.metaplex.amount.isZeroAmount(a))

        assertTrue(foundation.metaplex.amount.isPositiveAmount(foundation.metaplex.amount.sol(0)))
        assertFalse(foundation.metaplex.amount.isNegativeAmount(foundation.metaplex.amount.sol(0)))
        assertTrue(foundation.metaplex.amount.isZeroAmount(foundation.metaplex.amount.sol(0)))

        assertFalse(foundation.metaplex.amount.isPositiveAmount(foundation.metaplex.amount.sol(-1)))
        assertTrue(foundation.metaplex.amount.isNegativeAmount(foundation.metaplex.amount.sol(-1)))
        assertFalse(foundation.metaplex.amount.isZeroAmount(foundation.metaplex.amount.sol(-1)))
    }

    @Test
    fun testComparisonWithTolerance() {
        assertFalse(
            foundation.metaplex.amount.isEqualToAmount(
                foundation.metaplex.amount.sol(1.5),
                foundation.metaplex.amount.sol(1.6)
            )
        )
        assertFalse(
            foundation.metaplex.amount.isEqualToAmount(
                foundation.metaplex.amount.sol(1.5),
                foundation.metaplex.amount.sol(1.6),
                foundation.metaplex.amount.sol(0.01)
            )
        )
        assertTrue(
            foundation.metaplex.amount.isEqualToAmount(
                foundation.metaplex.amount.sol(1.5),
                foundation.metaplex.amount.sol(1.6),
                foundation.metaplex.amount.sol(0.1)
            )
        )
        assertTrue(
            foundation.metaplex.amount.isEqualToAmount(
                foundation.metaplex.amount.sol(1.5),
                foundation.metaplex.amount.sol(1.6),
                foundation.metaplex.amount.sol(0.2)
            )
        )
    }

    @Test
    fun testInstanceReturn() {
        val a = foundation.metaplex.amount.sol(1.5)
        val b = foundation.metaplex.amount.lamports(4200000000) // 4.2 SOL

        assertNotSame(a, foundation.metaplex.amount.addAmounts(a, b))
        assertNotSame(b, foundation.metaplex.amount.addAmounts(a, b))
        assertNotSame(a, foundation.metaplex.amount.subtractAmounts(a, b))
        assertNotSame(b, foundation.metaplex.amount.subtractAmounts(a, b))
        assertNotSame(a, foundation.metaplex.amount.multiplyAmount(a, 3))
        assertNotSame(a, foundation.metaplex.amount.divideAmount(a, 3))
    }

    @Test
    fun testCreatePercentAmounts() {
        amountEquals(foundation.metaplex.amount.percentAmount(5.5, 2), "5.50%")
        amountEquals(foundation.metaplex.amount.percentAmount(5.5, 2), "5.50%")
        amountEquals(foundation.metaplex.amount.percentAmount(5.5, 4), "5.5000%")
        amountEquals(foundation.metaplex.amount.percentAmount(5.12345, 4), "5.1234%")
        amountEquals(foundation.metaplex.amount.percentAmount(5.12345, 0), "5%")
        amountEquals(foundation.metaplex.amount.percentAmount(100.0, 2), "100.00%")
        amountEquals(foundation.metaplex.amount.percentAmount(250.0, 2), "250.00%")
    }

    private fun amountEquals(amount: foundation.metaplex.amount.Amount<*, *>, expected: String) {
        val formattedAmount = foundation.metaplex.amount.displayAmount(amount)
        assertEquals(expected, formattedAmount)
    }
}