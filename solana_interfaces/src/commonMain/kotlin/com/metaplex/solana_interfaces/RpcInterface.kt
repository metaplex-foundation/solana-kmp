package com.metaplex.solana_interfaces

import com.metaplex.solana_public_keys.PublicKey

/**
 * Defines the interface for an RPC client.
 * It allows us to interact with the Solana blockchain.
 *
 * @category Context and Interfaces
 */
interface RpcInterface {
    /** The RPC endpoint used by the client. */
    fun getEndpoint(): String

    /** The Solana cluster of the RPC being used. */
    fun getCluster(): Cluster

    /**
     * Fetch a raw account at the given address.
     *
     * @param publicKey The public key of the account to fetch.
     * @param options The options to use when fetching the account.
     * @returns A raw account that may or may not exist.
     */
    suspend fun getAccount(
        publicKey: PublicKey,
        options: RpcGetAccountOptions
    ): MaybeRpcAccount

    /**
     * Fetch multiple raw accounts at the given addresses.
     *
     * @param publicKey The public keys of the accounts to fetch.
     * @param options The options to use when fetching multiple accounts.
     * @returns An array of raw accounts that may or may not exist.
     */
    suspend fun getAccounts(
        publicKeys: List<PublicKey>,
        options: RpcGetAccountsOptions
    ): List<MaybeRpcAccount>

    /**
     * Fetch multiple raw accounts from a program.
     *
     * @param programId The public key of the program to fetch accounts from.
     * @param options The options to use when fetching program accounts.
     * @returns An array of raw accounts.
     */
    suspend fun getProgramAccounts(
        programId: PublicKey,
        options: RpcGetProgramAccountsOptions
    ): List<RpcAccount>

    /**
     * Whether or not an account at a given address exists.
     *
     * @param publicKey The public key of the account.
     * @param options The options to use when checking if an account exists.
     * @returns `true` if the account exists, `false` otherwise.
     */
    suspend fun accountExists( publicKey: PublicKey): Boolean
}

/**
 * The various commitment levels when fetching data from the blockchain.
 * @category Rpc
 */
sealed class Commitment(val value: String) {
    object Processed : Commitment("processed")
    object Confirmed : Commitment("confirmed")
    object Finalized : Commitment("finalized")
}

/**
 * An object to request a slice of data starting
 * at `offset` and ending at `offset + length`.
 * @category Rpc
 */
data class RpcDataSlice ( val offset: Number, val length: Number)

/**
 * Defines a filter to use when fetching program accounts.
 * @category Rpc
 */
sealed class RpcDataFilter

/**
 * Defines a filter that selects accounts by size.
 * @category Rpc
 */
data class RpcDataFilterSize(val dataSize: Number) : RpcDataFilter()

/**
 * Defines a filter that selects accounts by comparing
 * the given bytes at the given offset.
 * @category Rpc
 */
class Memcmp(val offset: Number, val bytes: ByteArray)
data class RpcDataFilterMemcmp(val memcmp: Memcmp) : RpcDataFilter()


/**
 * Defines an RPC result that wraps the returned value
 * and provides the slot number as context.
 * @category Rpc
 */
data class Context(val slot: Number)
open class RpcResultWithContext<Value>(
    open val context: Context,
    open val value: Value,
)

/**
 * Defines the common options re-used by all
 * the methods defines in the RPC interface.
 * @category Rpc
 */
open class RpcBaseOptions(
    /** An explicit RPC request identifier. */
    open val id: String?,
    /** The commitment level to use when fetching data. */
    open val commitment: Commitment?,
    /** The minimum slot to use when fetching data. */
    open val minContextSlot: Number?
)

/**
 * The options to use when fetching an account.
 * @category Rpc
 */
data class RpcGetAccountOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** Select only a portion of the account's data. */
    val dataSlice: RpcDataSlice?
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when fetching multiple accounts.
 * @category Rpc
 */
data class RpcGetAccountsOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** For each account, select only a portion of their data. */
    val dataSlice: RpcDataSlice?
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when fetching program accounts.
 * @category Rpc
 */
data class RpcGetProgramAccountsOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** For each program account, select only a portion of their data. */
    val dataSlice: RpcDataSlice?,
    /** A set of filters to narrow down the returned program accounts. Max 5 filters. */
    val filters: List<RpcDataFilter>
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when getting the block time of a slot.
 * @category Rpc
 */
typealias RpcGetBlockTimeOptions = RpcBaseOptions

/**
 * The options to use when fetching the balance of an account.
 * @category Rpc
 */
typealias RpcGetBalanceOptions = RpcBaseOptions

/**
 * The options to use when fetching the rent exempt amount.
 * @category Rpc
 */
data class RpcGetRentOptions (
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** @defaultValue `false` */
    val includesHeaderBytes: Boolean? = false
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when fetching the recent slot.
 * @category Rpc
 */
typealias RpcGetSlotOptions = RpcBaseOptions;

/**
 * The options to use when fetching the latest blockhash.
 * @category Rpc
 */
typealias RpcGetLatestBlockhashOptions = RpcBaseOptions;

/**
 * The options to use when fetching a transaction.
 * @category Rpc
 */
typealias RpcGetTransactionOptions = RpcBaseOptions;

/**
 * The options to use when fetching transaction statuses.
 * @category Rpc
 */
data class RpcGetSignatureStatusesOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /**
     * Enable searching status history, not needed for recent transactions.
     * @defaultValue `false`
     */
    val searchTransactionHistory: Boolean?
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when checking if an account exists.
 * @category Rpc
 */
typealias RpcAccountExistsOptions = RpcBaseOptions;

/**
 * The options to use when airdropping SOL.
 * @category Rpc
 */
typealias RpcAirdropOptions = RpcConfirmTransactionOptions

/**
 * The options to use when sending a custom RPC request.
 * @category Rpc
 */
data class RpcCallOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /**
     * By default, the RPC client pushes an additional `options`
     * parameter to the RPC request when a commitment is specified.
     * This `extra` parameter can be used to add more data to the
     * `options` parameter.
     */
    val extra: Any?
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when sending a transaction.
 * @category Rpc
 */
data class RpcSendTransactionOptions (
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** Whether to skip the preflight check. */
    val skipPreflight: Boolean?,
    /** The commitment level to use for the preflight check. */
    val preflightCommitment: Commitment?,
    /** The maximum number of retries to use. */
    val maxRetries: Number?
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * The options to use when confirming a transaction.
 * @category Rpc
 */
data class RpcConfirmTransactionOptions(
    override val id: String?,
    override val commitment: Commitment?,
    override val minContextSlot: Number?,
    /** The confirm strategy to use. */
    val strategy: RpcConfirmTransactionStrategy
): RpcBaseOptions(id, commitment, minContextSlot)

/**
 * Represents all the possible strategies to use when confirming a transaction.
 * @category Rpc
 */
sealed class RpcConfirmTransactionStrategy(open val type: String) {
    data class Blockhash(
        override val type: String,
        val blockhash: String,
        val lastValidBlockHeight: Int
    ) : RpcConfirmTransactionStrategy(type)
    data class DurableNonce(
        override val type: String,
        val minContextSlot: Int,
        val nonceAccountPubkey: PublicKey,
        val nonceValue: String
    ) : RpcConfirmTransactionStrategy(type)
}

/**
 * Defines the result of a transaction confirmation.
 * @category Rpc
 */
data class RpcConfirmTransactionResult(
    override val context: Context,
    override val value: TransactionError
): RpcResultWithContext<TransactionError>(context, value)