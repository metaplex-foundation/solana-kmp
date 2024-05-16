package foundation.metaplex.rpc

import foundation.metaplex.amount.SolAmount
import foundation.metaplex.rpc.serializers.AnchorAccountSerializer
import foundation.metaplex.rpc.serializers.BorshAsBase64JsonArraySerializer
import foundation.metaplex.rpc.serializers.PublicKeyAsStringSerializer
import foundation.metaplex.rpc.serializers.SolAmountSerializer
import foundation.metaplex.rpc.serializers.SolanaResponseSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 * Represents a hierarchy of classes and functions related to Solana accounts and serialization.
 */
sealed class AccountHeader {
    /**
     * Indicates whether the account is executable. (Program)
     */
    abstract val executable: Boolean

    /**
     * The owner of the account as a public key.
     */
    abstract val owner: PublicKey

    /**
     * The balance of the account in Solana lamports.
     */
    abstract val lamports: SolAmount

    /**
     * The epoch at which rent for the account was calculated, or null if not applicable.
     */
    abstract val rentEpoch: Long?
}

/**
 * Represents a generic account with data of type [T].
 *
 * @param executable Indicates whether the account is executable.
 * @param owner The owner of the account as a public key.
 * @param lamports The balance of the account in Solana lamports.
 * @param rentEpoch The epoch at which rent for the account was calculated.
 * @param data The account data of type [T].
 */
@Serializable
data class Account<T>(
    override val executable: Boolean,
    @Serializable(with = PublicKeyAsStringSerializer::class) override val owner: PublicKey,
    @Serializable(with = SolAmountSerializer::class) override val lamports: SolAmount,
    override val rentEpoch: Long,
    val data: T
) : AccountHeader()

/**
 * Creates a Solana account serializer for the specified data type [A].
 *
 * @param serializer The Kotlin serializer for data type [A].
 * @return An account serializer for the specified data type [A].
 */
@OptIn(ExperimentalSerializationApi::class)
fun <A> SolanaAccountSerializer(serializer: KSerializer<A>) =
    AccountInfoSerializer(
        BorshAsBase64JsonArraySerializer(
            AnchorAccountSerializer(serializer.descriptor.serialName, serializer)
        )
    )

/**
 * Creates an account info serializer for the specified data type [D].
 *
 * @param serializer The Kotlin serializer for data type [D].
 * @return An account info serializer for the specified data type [D].
 */
fun <D> AccountInfoSerializer(serializer: KSerializer<D>) =
    SolanaResponseSerializer(Account.serializer(serializer))

/**
 * Creates a Solana account serializer for the specified data type [A].
 *
 * @return An account serializer for the specified data type [A].
 */
inline fun <reified A> SolanaAccountSerializer() =
    AccountInfoSerializer<A?>(BorshAsBase64JsonArraySerializer(AnchorAccountSerializer()))
