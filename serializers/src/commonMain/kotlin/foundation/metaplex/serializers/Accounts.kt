package foundation.metaplex.serializers

import foundation.metaplex.amount.SolAmount
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 * Describes the header of an account.
 * @category Accounts
 */
sealed class AccountHeader {
    abstract val executable: Boolean
    abstract val owner: PublicKey
    abstract val lamports: SolAmount
    abstract val rentEpoch: Int?
}

/**
 * Describes a raw account that has not been deserialized.
 * @category Accounts
 */
class RpcAccount(
    override val executable: Boolean,
    override val owner: PublicKey,
    override val lamports: SolAmount,
    override val rentEpoch: Int? = null,
    val publicKey: PublicKey,
    val data: ByteArray,
): AccountHeader()

/**
 * Describes a raw account that may or may not exist.
 * @category Accounts
 */
@Serializable
sealed class MaybeRpcAccount {
    data class Existing(val exists: Boolean, val rpcAccount: RpcAccount) : MaybeRpcAccount()
    data class NonExisting(val exists: Boolean, val publicKey: PublicKey) : MaybeRpcAccount()
}

/**
 * Describes a deserialized account.
 * @category Accounts
 */
@Serializable
data class Account<T>(
    override val executable: Boolean,
    @Serializable(with = PublicKeyAsStringSerializer::class) override val owner: PublicKey,
    @Serializable(with = SolAmountSerializer::class) override val lamports: SolAmount,
    override val rentEpoch: Int,
    val data: T
): AccountHeader()

fun <A> SolanaAccountSerializer(serializer: KSerializer<A>) =
    AccountInfoSerializer(
        BorshAsBase64JsonArraySerializer(
            AnchorAccountSerializer(serializer.descriptor.serialName, serializer)
        )
    )

fun <D> AccountInfoSerializer(serializer: KSerializer<D>) =
    SolanaResponseSerializer(Account.serializer(serializer))

inline fun <reified A> SolanaAccountSerializer() =
    AccountInfoSerializer<A?>(BorshAsBase64JsonArraySerializer(AnchorAccountSerializer()))
