package com.metaplex.solana_interfaces

import com.metaplex.solana_interfaces.errors.SolanaError
import com.metaplex.solana_public_keys.PublicKey

/**
 * Describes the header of an account.
 * @category Accounts
 */
open class AccountHeader(
    open val executable: Boolean,
    open val owner: PublicKey,
    open val lamports: SolAmount,
    open val rentEpoch: Int?
)

/**
 * Describes a raw account that has not been deserialized.
 * @category Accounts
 */
data class RpcAccount(
    override val executable: Boolean,
    override val owner: PublicKey,
    override val lamports: SolAmount,
    override val rentEpoch: Int? = null,
    val publicKey: PublicKey,
    val data: ByteArray,
): AccountHeader(executable, owner, lamports, rentEpoch)

/**
 * Describes a raw account that may or may not exist.
 * @category Accounts
 */
sealed class MaybeRpcAccount {
    data class Existing(val exists: Boolean, val rpcAccount: RpcAccount) : MaybeRpcAccount()
    data class NonExisting(val exists: Boolean, val publicKey: PublicKey) : MaybeRpcAccount()
}

/**
 * Describes a deserialized account.
 * @category Accounts
 */
data class Account<T : Any>(
    val publicKey: PublicKey,
    val header: AccountHeader,
    val data: T
)

/**
 * Given an account data serializer,
 * returns a deserialized account from a raw account.
 * @category Accounts
 */
fun <From : Any, To : From> deserializeAccount(
    rawAccount: RpcAccount,
    dataSerializer: Serializer<From, To>
): Account<To> {
    val (executable, owner, lamports, rentEpoch, publicKey, data) = rawAccount
    try {
        val parsedData = dataSerializer.deserialize(data).first
        return Account(owner, AccountHeader(executable, owner, lamports, rentEpoch), parsedData)
    } catch (error: Throwable) {
        throw SolanaError.UnexpectedAccountError(
            owner,
            dataSerializer.description,
            error
        )
    }
}
