package foundation.metaplex.amount

import com.ionspin.kotlin.bignum.integer.BigInteger
import kotlin.math.absoluteValue
import kotlin.math.pow

/**
 * The identifier of an amount.
 * @category Utils — Amounts
 */
typealias AmountIdentifier = String

/**
 * The number of decimals in an amount represented using the lowest possible unit.
 * @category Utils — Amounts
 */
typealias AmountDecimals = Int

/**
 * Describes an amount of any type or currency using the lowest possible unit.
 * It uses a BigInteger to represent the basis points of the amount, a decimal number
 * to know how to interpret the basis points, and an identifier to know what
 * type of amount we are dealing with.
 *
 * Custom type parameters can be used to represent specific types of amounts.
 * For example:
 * - Amount<'SOL', 9> represents an amount of SOL in lamports.
 * - Amount<'USD', 2> represents an amount of USD in cents.
 * - Amount<'%', 2> represents a percentage with 2 decimals.
 *
 * @category Utils — Amounts
 */
data class Amount<I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals>(
    val basisPoints: BigInteger,
    val identifier: I,
    val decimals: D
)

/**
 * An amount of SOL represented using the lowest possible unit — i.e. lamports.
 * @category Utils — Amounts
 */
typealias SolAmount = foundation.metaplex.amount.Amount<String, Int>

/**
 * A percentage represented in basis points using a given number of decimals.
 * @category Utils — Amounts
 */
typealias PercentAmount<D> = foundation.metaplex.amount.Amount<String, D>

/**
 * Creates an amount from the provided basis points, identifier, and decimals.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> createAmount(
    basisPoints: BigInteger,
    identifier: I,
    decimals: D
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.Amount(basisPoints, identifier, decimals)
}

fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> createAmount(
    basisPoints: Int,
    identifier: I,
    decimals: D
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.createAmount(BigInteger(basisPoints), identifier, decimals)
}

fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> createAmount(
    basisPoints: Long,
    identifier: I,
    decimals: D
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.createAmount(BigInteger(basisPoints), identifier, decimals)
}

/**
 * Creates an amount from a decimal value which will be converted to the lowest
 * possible unit using the provided decimals.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> createAmountFromDecimals(
    decimalAmount: Double,
    identifier: I,
    decimals: D
): foundation.metaplex.amount.Amount<I, D> {
    val exponentAmount = foundation.metaplex.amount.createAmount(
        BigInteger.tryFromDouble(10.0.pow(decimals)),
        identifier,
        decimals
    )
    return foundation.metaplex.amount.multiplyAmount(exponentAmount, decimalAmount)
}

/**
 * Creates a percentage amount from the provided decimal value.
 * @category Utils — Amounts
 */
fun <D : foundation.metaplex.amount.AmountDecimals> percentAmount(
    percent: Double,
    decimals: D = 2 as D
): foundation.metaplex.amount.PercentAmount<D> {
    return foundation.metaplex.amount.createAmountFromDecimals(percent, "%", decimals)
}

/**
 * Creates an amount of SPL tokens from the provided decimal value.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> tokenAmount(
    tokens: Double,
    identifier: I? = null,
    decimals: D? = null
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.createAmountFromDecimals(
        tokens,
        (identifier ?: "splToken") as I,
        (decimals ?: 0) as D
    )
}

fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> tokenAmount(
    tokens: Int,
    identifier: I? = null,
    decimals: D? = null
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.tokenAmount(
        tokens.toDouble(),
        (identifier ?: "splToken") as I,
        (decimals ?: 0) as D
    )
}

/**
 * Creates a SolAmount from the provided lamports.
 * @category Utils — Amounts
 */
fun lamports(lamports: BigInteger): foundation.metaplex.amount.SolAmount =
    foundation.metaplex.amount.createAmount(lamports, "SOL", 9)
fun lamports(lamports: Int): foundation.metaplex.amount.SolAmount =
    foundation.metaplex.amount.createAmount(BigInteger(lamports), "SOL", 9)
fun lamports(lamports: Long): foundation.metaplex.amount.SolAmount =
    foundation.metaplex.amount.createAmount(BigInteger(lamports), "SOL", 9)

/**
 * Creates a SolAmount from the provided decimal value in SOL.
 * @category Utils — Amounts
 */
fun sol(sol: Int): foundation.metaplex.amount.SolAmount {
    return foundation.metaplex.amount.sol(sol.toDouble())
}
fun sol(sol: Double): foundation.metaplex.amount.SolAmount {
    return foundation.metaplex.amount.createAmountFromDecimals(sol, "SOL", 9)
}

/**
 * Determines whether two amounts are of the same type.
 * @category Utils — Amounts
 */
fun sameAmounts(left: foundation.metaplex.amount.Amount<*, *>, right: foundation.metaplex.amount.Amount<*, *>): Boolean {
    return foundation.metaplex.amount.isAmount(left, right.identifier, right.decimals)
}

/**
 * Determines whether a given amount has the provided identifier and decimals.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isAmount(
    amount: foundation.metaplex.amount.Amount<*, *>,
    identifier: I,
    decimals: D
): Boolean {
    return amount.identifier == identifier && amount.decimals == decimals
}

/**
 * Ensures that a given amount has the provided identifier and decimals.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> assertAmount(
    amount: foundation.metaplex.amount.Amount<*, *>,
    identifier: I,
    decimals: D
) {
    if (!foundation.metaplex.amount.isAmount(amount, identifier, decimals)) {
        throw foundation.metaplex.amount.AmountErrors.UnexpectedAmountError(
            amount,
            identifier,
            decimals
        )
    }
}

/**
 * Ensures that a given amount is a SolAmount.
 * @category Utils — Amounts
 */
fun assertSolAmount(actual: foundation.metaplex.amount.Amount<*, *>) {
    foundation.metaplex.amount.assertAmount(actual, "SOL", 9)
}

/**
 * Ensures that two amounts are of the same type.
 * @category Utils — Amounts
 */
fun assertSameAmounts(
    left: foundation.metaplex.amount.Amount<*, *>,
    right: foundation.metaplex.amount.Amount<*, *>,
    operation: String? = null
) {
    if (!foundation.metaplex.amount.sameAmounts(left, right)) {
        throw foundation.metaplex.amount.AmountErrors.AmountMismatchError(left, right, operation)
    }
}

/**
 * Adds two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> addAmounts(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): foundation.metaplex.amount.Amount<I, D> {
    foundation.metaplex.amount.assertSameAmounts(left, right, "add")

    return foundation.metaplex.amount.Amount(
        basisPoints = left.basisPoints + right.basisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Subtracts two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> subtractAmounts(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): foundation.metaplex.amount.Amount<I, D> {
    foundation.metaplex.amount.assertSameAmounts(left, right, "subtract")

    return foundation.metaplex.amount.Amount(
        basisPoints = left.basisPoints - right.basisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Multiplies an amount by a given multiplier.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> multiplyAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    multiplier: Number
): foundation.metaplex.amount.Amount<I, D> {
    val (units, decimals) = multiplier.toDouble().toString().split(".")
    val multiplierBasisPointsString = units + decimals
    val multiplierBasisPoints = BigInteger.parseString(multiplierBasisPointsString)
    val multiplierExponents = BigInteger(10).pow(BigInteger(decimals.length))

    return foundation.metaplex.amount.Amount(
        basisPoints = (left.basisPoints * multiplierBasisPoints) / multiplierExponents,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> multiplyAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    multiplier: BigInteger
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.Amount(
        basisPoints = (left.basisPoints * multiplier),
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Divides an amount by a given divisor.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> divideAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    divisor: BigInteger
): foundation.metaplex.amount.Amount<I, D> {
    return foundation.metaplex.amount.Amount(
        basisPoints = left.basisPoints / divisor,
        identifier = left.identifier,
        decimals = left.decimals
    )
}
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> divideAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    divisor: Number
): foundation.metaplex.amount.Amount<I, D> {
    val divisorBasisPoints = BigInteger.tryFromDouble(divisor.toDouble(), true)

    return foundation.metaplex.amount.Amount(
        basisPoints = left.basisPoints / divisorBasisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Returns the absolute value of an amount.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> absoluteAmount(
    value: foundation.metaplex.amount.Amount<I, D>
): foundation.metaplex.amount.Amount<I, D> {
    val x = value.basisPoints
    return foundation.metaplex.amount.Amount(
        basisPoints = if (x < BigInteger.ZERO) -x else x,
        identifier = value.identifier,
        decimals = value.decimals
    )
}

/**
 * Compares two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> compareAmounts(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): Int {
    foundation.metaplex.amount.assertSameAmounts(left, right, "compare")

    return when {
        left.basisPoints > right.basisPoints -> 1
        left.basisPoints < right.basisPoints -> -1
        else -> 0
    }
}

/**
 * Determines whether two amounts are equal.
 * An optional tolerance can be provided to allow for small differences.
 * When using {@link SolAmount}, this is usually due to transaction or small storage fees.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isEqualToAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>,
    tolerance: foundation.metaplex.amount.Amount<I, D>? = null
): Boolean {
    val delta = foundation.metaplex.amount.absoluteAmount(
        foundation.metaplex.amount.subtractAmounts(
            left,
            right
        )
    )
    val toleranceOrDefault = tolerance ?: foundation.metaplex.amount.createAmount(
        BigInteger.ZERO,
        left.identifier,
        left.decimals
    )

    foundation.metaplex.amount.assertSameAmounts(left, right, "isEqualToAmount")
    foundation.metaplex.amount.assertSameAmounts(left, toleranceOrDefault, "isEqualToAmount")

    return foundation.metaplex.amount.isLessThanOrEqualToAmount(delta, toleranceOrDefault)
}

/**
 * Whether the left amount is less than the right amount.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isLessThanAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): Boolean {
    return foundation.metaplex.amount.compareAmounts(left, right) < 0
}

/**
 * Whether the left amount is less than or equal to the right amount.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isLessThanOrEqualToAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): Boolean {
    return foundation.metaplex.amount.compareAmounts(left, right) <= 0
}

/**
 * Whether the left amount is greater than the right amount.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isGreaterThanAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): Boolean {
    return foundation.metaplex.amount.compareAmounts(left, right) > 0
}

/**
 * Whether the left amount is greater than or equal to the right amount.
 * @category Utils — Amounts
 */
fun <I : foundation.metaplex.amount.AmountIdentifier, D : foundation.metaplex.amount.AmountDecimals> isGreaterThanOrEqualToAmount(
    left: foundation.metaplex.amount.Amount<I, D>,
    right: foundation.metaplex.amount.Amount<I, D>
): Boolean {
    return foundation.metaplex.amount.compareAmounts(left, right) >= 0
}

/**
 * Whether the amount is zero.
 * @category Utils — Amounts
 */
fun isZeroAmount(value: foundation.metaplex.amount.Amount<*, *>): Boolean {
    return value.basisPoints == BigInteger.ZERO
}

/**
 * Whether the amount is positive.
 * @category Utils — Amounts
 */
fun isPositiveAmount(value: foundation.metaplex.amount.Amount<*, *>): Boolean {
    return value.basisPoints >= BigInteger.ZERO
}

/**
 * Whether the amount is negative.
 * @category Utils — Amounts
 */
fun isNegativeAmount(value: foundation.metaplex.amount.Amount<*, *>): Boolean {
    return value.basisPoints < BigInteger.ZERO
}

/**
 * Converts an amount to a string by using the amount's decimals.
 * @category Utils — Amounts
 */
fun amountToString(value: foundation.metaplex.amount.Amount<*, *>, maxDecimals: Int? = null): String {
    var text = value.basisPoints.toString()
    if (value.decimals == 0) {
        return text
    }

    val sign = if (text.startsWith('-')) "-" else ""
    text = text.replace("-", "")
    text = text.padStart(value.decimals + 1, '0')
    val units = text.substring(0, text.length - value.decimals)
    var decimals = text.substring(text.length - value.decimals)

    maxDecimals?.let {
        decimals = decimals.substring(0, it)
    }

    return "$sign$units.$decimals"
}

/**
 * Converts an amount to a number by using the amount's decimals.
 * Note that this may throw an error if the amount is too large to fit in a Kotlin number.
 * @category Utils — Amounts
 */
fun amountToNumber(value: foundation.metaplex.amount.Amount<*, *>): Double {
    return foundation.metaplex.amount.amountToString(value).toDouble()
}

/**
 * Displays an amount as a string by using the amount's decimals and identifier.
 * @category Utils — Amounts
 */
fun displayAmount(value: foundation.metaplex.amount.Amount<*, *>, maxDecimals: Int? = null): String {
    val amountAsString = foundation.metaplex.amount.amountToString(value, maxDecimals)

    return when (value.identifier) {
        "%" -> "$amountAsString%"
        "splToken" -> if (amountAsString.toDouble().absoluteValue == 1.0 ) {
            "$amountAsString Token"
        } else {
            "$amountAsString Tokens"
        }
        else -> if (value.identifier.startsWith("splToken.")) {
            val identifier = value.identifier.split('.')[1]
            "$identifier $amountAsString"
        } else {
            "${value.identifier} $amountAsString"
        }
    }
}
