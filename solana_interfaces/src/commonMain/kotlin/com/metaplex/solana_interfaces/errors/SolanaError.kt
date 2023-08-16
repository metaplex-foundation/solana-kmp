package com.metaplex.solana_interfaces.errors

import com.metaplex.solana_interfaces.Amount
import com.metaplex.solana_interfaces.AmountDecimals
import com.metaplex.solana_interfaces.AmountIdentifier
import com.metaplex.solana_public_keys.PublicKey

sealed class SolanaError(message: String) : Exception(message){
    data class SdkError(override val message: String): SolanaError(message)
    data class UnexpectedAccountError(
            val publicKey: PublicKey,
            val expectedType: String,
            val error: Throwable
        ): SolanaError("The account at the provided address [${publicKey}] is not of the expected type [${expectedType}].")
    data class UnexpectedAmountError(
            val amount: Amount<*, *>,
            val expectedIdentifier: AmountIdentifier,
            val expectedDecimals: AmountDecimals
        ): SolanaError(
                "Expected amount of type [${expectedIdentifier} with ${expectedDecimals} decimals] " +
                "but got [${amount.identifier} with ${amount.decimals} decimals]. " +
                "Ensure the provided Amount is of the expected type."
        )
    data class AmountMismatchError(val left: Amount<*, *>, val right: Amount<*, *>, val operation: String?): SolanaError(
            "The SDK tried to execute an operation[${operation}] on two amounts of different types: " +
            "[${left.identifier} with ${left.decimals} decimals] and " +
            "[${right.identifier} with ${right.decimals} decimals]. " +
            "Provide both amounts in the same type to perform this operation.")
}