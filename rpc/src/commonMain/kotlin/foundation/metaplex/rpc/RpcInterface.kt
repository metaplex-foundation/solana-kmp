package foundation.metaplex.rpc

import foundation.metaplex.amount.SolAmount
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

import com.solana.publickey.SolanaPublicKey as PublicKey

/**
 * Represents an interface for making remote procedure calls (RPC).
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("RpcInterface")
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
     * Retrieves information for multiple accounts using their public keys.
     *
     * This function is a part of the RpcInterface and is used to fetch the information of multiple accounts from the blockchain in a single RPC call. The accounts are specified using a list of their public keys.
     *
     * @param publicKeys A list of [PublicKey] objects representing the public keys of the accounts whose information needs to be fetched.
     * @param configuration An optional configuration object of type [RpcGetMultipleAccountsConfiguration] that can be used to specify various options for the RPC request such as encoding format, commitment level, minimum context slot, and data slice. Defaults to Base64 encoding if not specified.
     * @param serializer A [KSerializer] object to deserialize the response into an appropriate data type.
     *
     * @return A list of [Account] objects containing the information of the accounts, or null if not found. The list can contain null elements if information for some accounts could not be retrieved.
     *
     * @throws Exception if the RPC call fails for reasons such as network issues, incorrect inputs, etc.
     */
    suspend fun <T> getMultipleAccounts(
        publicKeys: List<PublicKey>,
        configuration: RpcGetMultipleAccountsConfiguration?,
        serializer: KSerializer<T>,
    ): List<Account<T>?>?

    /**
     * Retrieves all accounts associated with a specific program ID.
     *
     * This method facilitates the fetching of all accounts that are associated with a given program ID. It performs an RPC (Remote Procedure Call) to retrieve a list of accounts, leveraging the specified serializer to handle the deserialization of the account data.
     *
     * @param programId The [PublicKey] representing the ID of the program whose accounts need to be fetched.
     * @param configuration An optional configuration object of type [RpcGetProgramAccountsConfiguration] that can be used to specify various options for the RPC request such as encoding format, commitment level, minimum context slot, and data slice. Defaults to any default values specified in [RpcGetProgramAccountsConfiguration] if not provided.
     * @param serializer A [KSerializer] instance used to deserialize the response into the appropriate data type, ensuring the structured data can be worked with in a type-safe manner within your Kotlin application.
     *
     * @return A list of [Account] objects containing information of all the accounts associated with the specified program ID. The list can contain null elements if information for some accounts could not be retrieved. Returns null if the request fails entirely.
     *
     * @throws Exception  if the RPC call fails for reasons such as network issues, incorrect inputs, etc.
     */
    suspend fun <T> getProgramAccounts(
        programId: PublicKey,
        configuration: RpcGetProgramAccountsConfiguration?,
        serializer: KSerializer<T>
    ): List<Account<T>?>?

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
     * Fetch the slot.
     *
     * @param options The options to use when fetching the slot.
     * @returns The current slot.
     */
    suspend fun getSlot(
        configuration: RpcGetSlotConfiguration?
    ): ULong

    /**
     * Fetch the Minimum Balance For Rent Exemption.
     *
     * @param usize The Account's data length
     * @returns The Minimum Balance For Rent Exemption.
     */
    suspend fun getMinimumBalanceForRentExemption(
        usize: ULong
    ): ULong

    /**
     * Requests an airdrop of tokens to the specified account on the Solana blockchain.
     *
     * @param configuration The configuration for the airdrop request.
     * @return A transaction signature representing the result of the airdrop request.
     */
    suspend fun requestAirdrop(
        configuration: RpcRequestAirdropConfiguration
    ): TransactionSignature

    /**
     * Fetch the Balance of the account of provided Pubkey
     *
     * @param Pubkey of account to query, as base-58 encoded string
     * @returns The balance of the account of provided Pubkey
     */
    suspend fun getBalance(
        publicKey: PublicKey,
        configuration: RpcGetBalanceConfiguration?
    ): Long

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
data class RpcGetMultipleAccountsConfiguration(
    override val encoding: Encoding = Encoding.base64,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
    val dataSlice: RpcDataSlice? = null
): RpcBaseOptions

@Serializable
data class RpcGetProgramAccountsConfiguration(
    override val encoding: Encoding = Encoding.base64,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
    val dataSlice: RpcDataSlice? = null,
    val filters: List<RpcDataFilter>? = null
): RpcBaseOptions

@Serializable
data class RpcGetLatestBlockhashConfiguration(
    override val encoding: Encoding? = null,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
): RpcBaseOptions

@Serializable
data class RpcGetSlotConfiguration(
    override val encoding: Encoding? = null,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
): RpcBaseOptions

@Serializable
data class RpcGetBalanceConfiguration(
    override val encoding: Encoding? = null,
    override val commitment: Commitment? = null,
    override val minContextSlot: ULong? = null,
): RpcBaseOptions
data class RpcRequestAirdropConfiguration(
    val publicKey: PublicKey,
    val lamports: SolAmount,
    val commitment: Commitment? = null,
)

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
    base64,
    jsonParsed,
    base58,
    @SerialName("base64+zstd")
    base64_zstd
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

