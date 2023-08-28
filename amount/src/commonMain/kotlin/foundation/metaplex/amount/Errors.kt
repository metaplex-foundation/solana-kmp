package foundation.metaplex.amount

sealed class AmountErrors(message: String): Exception(message) {
    data class UnexpectedAmountError(
        val amount: foundation.metaplex.amount.Amount<*, *>,
        val expectedIdentifier: foundation.metaplex.amount.AmountIdentifier,
        val expectedDecimals: foundation.metaplex.amount.AmountDecimals
    ): AmountErrors(
        "Expected amount of type [${expectedIdentifier} with ${expectedDecimals} decimals] " +
                "but got [${amount.identifier} with ${amount.decimals} decimals]. " +
                "Ensure the provided Amount is of the expected type."
    )
    data class AmountMismatchError(val left: foundation.metaplex.amount.Amount<*, *>, val right: foundation.metaplex.amount.Amount<*, *>, val operation: String?): AmountErrors(
        "The SDK tried to execute an operation[${operation}] on two amounts of different types: " +
                "[${left.identifier} with ${left.decimals} decimals] and " +
                "[${right.identifier} with ${right.decimals} decimals]. " +
                "Provide both amounts in the same type to perform this operation.")
}

