package foundation.metaplex.rpc

import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 * Represents an interface for making remote procedure calls (RPC).
 */
interface RpcInterface {

    /**
     * Retrieves account information for a given public key.
     *
     * @param publicKey The public key of the account.
     * @param configuration Optional configuration for the RPC request. Defaults to Base64 Encoding
     * @param serializer The serializer used for deserializing the response.
     * @return An [Account] object representing the account information, or null if not found.
     */
    suspend fun <T> getAccountInfo(
        publicKey: PublicKey,
        configuration: RpcGetAccountInfoConfiguration?,
        serializer: KSerializer<T>,
    ): Account<T>?

    /**
     * Fetch the latest blockhash.
     *
     * @param options The options to use when fetching the latest blockhash.
     * @returns The latest blockhash and its block height.
     */
    suspend fun getLatestBlockhash(
        configuration: RpcGetLatestBlockhashConfiguration?
    ): BlockhashWithExpiryBlockHeight


    /**
     * Send a transaction to the blockchain.
     *
     * @param transaction The transaction to send.
     * @param configuration The configuration to use when sending a transaction.
     * @returns The signature of the sent transaction.
     */
    suspend fun sendTransaction(
        transaction: SerializedTransaction,
        configuration: RpcSendTransactionConfiguration?
    ): TransactionSignature
}

@Serializable
sealed interface RpcBaseOptions {

    /**
     * The encoding format to be used for the transaction.
     * Defaults to Base64 encoding.
     */
    val encoding: Encoding?

    /**
     * The commitment level to be applied when sending the transaction.
     * It can be null, indicating that the default commitment level should be used.
     */
    val commitment: Commitment?

    /**
     * The minimum context slot to be applied when sending the transaction.
     * It can be null, indicating that the default minimum context slot should be used.
     */
    val minContextSlot: ULong?
}

/**
 * Configuration input for the [RpcInterface.sendTransaction] function.
 */
@Serializable
data class RpcGetAccountInfoConfiguration(
    override val encoding: Encoding = Encoding.base64,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
    val dataSlice: RpcDataSlice? = null
): RpcBaseOptions

/**
 * Configuration input for the [RpcInterface.getAccountInfo] function.
 */
@Serializable
data class RpcSendTransactionConfiguration(
    override val encoding: Encoding = Encoding.base64,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
    /** Whether to skip the preflight check. */
    val skipPreflight: Boolean? = null,
    /** The commitment level to use for the preflight check. */
    val preflightCommitment: Commitment? = null,
    /** The maximum number of retries to use. */
    val maxRetries: UInt? = null,
): RpcBaseOptions

@Serializable
data class RpcGetLatestBlockhashConfiguration(
    override val encoding: Encoding? = null,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
): RpcBaseOptions

/**
 * Enumeration representing the commitment level for RPC requests.
 */
@Serializable
enum class Commitment {
    processed,
    confirmed,
    finalized
}

/**
 * Represents a data slice used in RPC configuration.
 */
@Serializable
data class RpcDataSlice(val offset: ULong, val length: ULong)

/**
 * Sealed class representing data filters for RPC requests.
 */
@Serializable
sealed class RpcDataFilter

/**
 * Represents a data filter based on data size.
 */
@Serializable
data class RpcDataFilterSize(val dataSize: ULong) : RpcDataFilter()

/**
 * Represents a memory comparison operation used as a data filter.
 */
@Serializable
class Memcmp(val offset: ULong, val bytes: ByteArray)

/**
 * Represents a data filter based on memory comparison.
 */
@Serializable
data class RpcDataFilterMemcmp(val memcmp: Memcmp) : RpcDataFilter()

/**
 * Enumeration representing different encoding formats for RPC requests.
 */
@Serializable
enum class Encoding {
    base64
}

/**
 * Defines a TransactionSignature.
 */
typealias TransactionSignature = ByteArray

/**
 * Defines a SerializedTransaction.
 */
typealias SerializedTransaction = ByteArray

/**
 * Defines a blockhash.
 */
typealias Blockhash = String

/**
 * Defines a blockhash with its expiry block height.
 */
@Serializable
data class BlockhashWithExpiryBlockHeight(
    val blockhash: Blockhash,
    val lastValidBlockHeight: ULong,
)