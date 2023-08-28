package foundation.metaplex.solanainterfaces.errors

import foundation.metaplex.amount.AmountErrors
import foundation.metaplex.solanapublickeys.PublicKey

sealed class SolanaError(message: String) : Exception(message){
    data class SdkError(override val message: String): SolanaError(message)
    data class UnexpectedAccountError(
            val publicKey: PublicKey,
            val expectedType: String,
            val error: Throwable
        ): SolanaError("The account at the provided address [${publicKey}] is not of the expected type [${expectedType}].")
    data class AmountError(val amountError: AmountErrors): SolanaError(amountError.message ?: "")
}