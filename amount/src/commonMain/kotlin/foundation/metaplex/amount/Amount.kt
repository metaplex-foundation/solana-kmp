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
data class Amount<I : AmountIdentifier, D : AmountDecimals>(
    val basisPoints: BigInteger,
    val identifier: I,
    val decimals: D
)

/**
 * An amount of SOL represented using the lowest possible unit — i.e. lamports.
 * @category Utils — Amounts
 */
typealias SolAmount = Amount<String, Int>

/**
 * A percentage represented in basis points using a given number of decimals.
 * @category Utils — Amounts
 */
typealias PercentAmount<D> = Amount<String, D>

/**
 * Creates an amount from the provided basis points, identifier, and decimals.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> createAmount(
    basisPoints: BigInteger,
    identifier: I,
    decimals: D
): Amount<I, D> {
    return Amount(basisPoints, identifier, decimals)
}

fun <I : AmountIdentifier, D : AmountDecimals> createAmount(
    basisPoints: Int,
    identifier: I,
    decimals: D
): Amount<I, D> {
    return createAmount(BigInteger(basisPoints), identifier, decimals)
}

fun <I : AmountIdentifier, D : AmountDecimals> createAmount(
    basisPoints: Long,
    identifier: I,
    decimals: D
): Amount<I, D> {
    return createAmount(BigInteger(basisPoints), identifier, decimals)
}

/**
 * Creates an amount from a decimal value which will be converted to the lowest
 * possible unit using the provided decimals.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> createAmountFromDecimals(
    decimalAmount: Double,
    identifier: I,
    decimals: D
): Amount<I, D> {
    val exponentAmount = createAmount(
        BigInteger.tryFromDouble(10.0.pow(decimals)),
        identifier,
        decimals
    )
    return multiplyAmount(exponentAmount, decimalAmount)
}

/**
 * Creates a percentage amount from the provided decimal value.
 * @category Utils — Amounts
 */
fun <D : AmountDecimals> percentAmount(
    percent: Double,
    decimals: D = 2 as D
): PercentAmount<D> {
    return createAmountFromDecimals(percent, "%", decimals)
}

/**
 * Creates an amount of SPL tokens from the provided decimal value.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> tokenAmount(
    tokens: Double,
    identifier: I? = null,
    decimals: D? = null
): Amount<I, D> {
    return createAmountFromDecimals(
        tokens,
        (identifier ?: "splToken") as I,
        (decimals ?: 0) as D
    )
}

fun <I : AmountIdentifier, D : AmountDecimals> tokenAmount(
    tokens: Int,
    identifier: I? = null,
    decimals: D? = null
): Amount<I, D> {
    return tokenAmount(
        tokens.toDouble(),
        (identifier ?: "splToken") as I,
        (decimals ?: 0) as D
    )
}

/**
 * Creates a SolAmount from the provided lamports.
 * @category Utils — Amounts
 */
fun lamports(lamports: BigInteger): SolAmount =
    createAmount(lamports, "SOL", 9)
fun lamports(lamports: Int): SolAmount =
    createAmount(BigInteger(lamports), "SOL", 9)
fun lamports(lamports: Long): SolAmount =
    createAmount(BigInteger(lamports), "SOL", 9)

/**
 * Creates a SolAmount from the provided decimal value in SOL.
 * @category Utils — Amounts
 */
fun sol(sol: Int): SolAmount {
    return sol(sol.toDouble())
}
fun sol(sol: Double): SolAmount {
    return createAmountFromDecimals(sol, "SOL", 9)
}

/**
 * Determines whether two amounts are of the same type.
 * @category Utils — Amounts
 */
fun sameAmounts(left: Amount<*, *>, right: Amount<*, *>): Boolean {
    return isAmount(left, right.identifier, right.decimals)
}

/**
 * Determines whether a given amount has the provided identifier and decimals.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> isAmount(
    amount: Amount<*, *>,
    identifier: I,
    decimals: D
): Boolean {
    return amount.identifier == identifier && amount.decimals == decimals
}

/**
 * Ensures that a given amount has the provided identifier and decimals.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> assertAmount(
    amount: Amount<*, *>,
    identifier: I,
    decimals: D
) {
    if (!isAmount(amount, identifier, decimals)) {
        throw AmountErrors.UnexpectedAmountError(
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
fun assertSolAmount(actual: Amount<*, *>) {
    assertAmount(actual, "SOL", 9)
}

/**
 * Ensures that two amounts are of the same type.
 * @category Utils — Amounts
 */
fun assertSameAmounts(
    left: Amount<*, *>,
    right: Amount<*, *>,
    operation: String? = null
) {
    if (!sameAmounts(left, right)) {
        throw AmountErrors.AmountMismatchError(left, right, operation)
    }
}

/**
 * Adds two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> addAmounts(
    left: Amount<I, D>,
    right: Amount<I, D>
): Amount<I, D> {
    assertSameAmounts(left, right, "add")

    return Amount(
        basisPoints = left.basisPoints + right.basisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Subtracts two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> subtractAmounts(
    left: Amount<I, D>,
    right: Amount<I, D>
): Amount<I, D> {
    assertSameAmounts(left, right, "subtract")

    return Amount(
        basisPoints = left.basisPoints - right.basisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Multiplies an amount by a given multiplier.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> multiplyAmount(
    left: Amount<I, D>,
    multiplier: Number
): Amount<I, D> {
    val (units, decimals) = multiplier.toDouble().toString().split(".")
    val multiplierBasisPointsString = units + decimals
    val multiplierBasisPoints = BigInteger.parseString(multiplierBasisPointsString)
    val multiplierExponents = BigInteger(10).pow(BigInteger(decimals.length))

    return Amount(
        basisPoints = (left.basisPoints * multiplierBasisPoints) / multiplierExponents,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

fun <I : AmountIdentifier, D : AmountDecimals> multiplyAmount(
    left: Amount<I, D>,
    multiplier: BigInteger
): Amount<I, D> {
    return Amount(
        basisPoints = (left.basisPoints * multiplier),
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Divides an amount by a given divisor.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> divideAmount(
    left: Amount<I, D>,
    divisor: BigInteger
): Amount<I, D> {
    return Amount(
        basisPoints = left.basisPoints / divisor,
        identifier = left.identifier,
        decimals = left.decimals
    )
}
fun <I : AmountIdentifier, D : AmountDecimals> divideAmount(
    left: Amount<I, D>,
    divisor: Number
): Amount<I, D> {
    val divisorBasisPoints = BigInteger.tryFromDouble(divisor.toDouble(), true)

    return Amount(
        basisPoints = left.basisPoints / divisorBasisPoints,
        identifier = left.identifier,
        decimals = left.decimals
    )
}

/**
 * Returns the absolute value of an amount.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> absoluteAmount(
    value: Amount<I, D>
): Amount<I, D> {
    val x = value.basisPoints
    return Amount(
        basisPoints = if (x < BigInteger.ZERO) -x else x,
        identifier = value.identifier,
        decimals = value.decimals
    )
}

/**
 * Compares two amounts of the same type.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> compareAmounts(
    left: Amount<I, D>,
    right: Amount<I, D>
): Int {
    assertSameAmounts(left, right, "compare")

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
fun <I : AmountIdentifier, D : AmountDecimals> isEqualToAmount(
    left: Amount<I, D>,
    right: Amount<I, D>,
    tolerance: Amount<I, D>? = null
): Boolean {
    val delta = absoluteAmount(
        subtractAmounts(
            left,
            right
        )
    )
    val toleranceOrDefault = tolerance ?: createAmount(
        BigInteger.ZERO,
        left.identifier,
        left.decimals
    )

    assertSameAmounts(left, right, "isEqualToAmount")
    assertSameAmounts(left, toleranceOrDefault, "isEqualToAmount")

    return isLessThanOrEqualToAmount(delta, toleranceOrDefault)
}

/**
 * Whether the left amount is less than the right amount.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> isLessThanAmount(
    left: Amount<I, D>,
    right: Amount<I, D>
): Boolean {
    return compareAmounts(left, right) < 0
}

/**
 * Whether the left amount is less than or equal to the right amount.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> isLessThanOrEqualToAmount(
    left: Amount<I, D>,
    right: Amount<I, D>
): Boolean {
    return compareAmounts(left, right) <= 0
}

/**
 * Whether the left amount is greater than the right amount.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> isGreaterThanAmount(
    left: Amount<I, D>,
    right: Amount<I, D>
): Boolean {
    return compareAmounts(left, right) > 0
}

/**
 * Whether the left amount is greater than or equal to the right amount.
 * @category Utils — Amounts
 */
fun <I : AmountIdentifier, D : AmountDecimals> isGreaterThanOrEqualToAmount(
    left: Amount<I, D>,
    right: Amount<I, D>
): Boolean {
    return compareAmounts(left, right) >= 0
}

/**
 * Whether the amount is zero.
 * @category Utils — Amounts
 */
fun isZeroAmount(value: Amount<*, *>): Boolean {
    return value.basisPoints == BigInteger.ZERO
}

/**
 * Whether the amount is positive.
 * @category Utils — Amounts
 */
fun isPositiveAmount(value: Amount<*, *>): Boolean {
    return value.basisPoints >= BigInteger.ZERO
}

/**
 * Whether the amount is negative.
 * @category Utils — Amounts
 */
fun isNegativeAmount(value: Amount<*, *>): Boolean {
    return value.basisPoints < BigInteger.ZERO
}

/**
 * Converts an amount to a string by using the amount's decimals.
 * @category Utils — Amounts
 */
fun amountToString(value: Amount<*, *>, maxDecimals: Int? = null): String {
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
fun amountToNumber(value: Amount<*, *>): Double {
    return amountToString(value).toDouble()
}

/**
 * Displays an amount as a string by using the amount's decimals and identifier.
 * @category Utils — Amounts
 */
fun displayAmount(value: Amount<*, *>, maxDecimals: Int? = null): String {
    val amountAsString = amountToString(value, maxDecimals)

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
