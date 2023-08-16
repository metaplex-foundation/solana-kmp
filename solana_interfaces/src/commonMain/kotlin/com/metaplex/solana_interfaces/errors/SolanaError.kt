package com.metaplex.solana_interfaces.errors

import com.metaplex.amount.AmountErrors
import com.metaplex.solana_public_keys.PublicKey

sealed class SolanaError(message: String) : Exception(message){
    data class SdkError(override val message: String): SolanaError(message)
    data class UnexpectedAccountError(
            val publicKey: PublicKey,
            val expectedType: String,
            val error: Throwable
        ): SolanaError("The account at the provided address [${publicKey}] is not of the expected type [${expectedType}].")
    data class AmountError(val amountError: AmountErrors): SolanaError(amountError.message ?: "")
}