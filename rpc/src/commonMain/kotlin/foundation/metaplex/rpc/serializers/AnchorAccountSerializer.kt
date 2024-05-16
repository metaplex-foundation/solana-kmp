package foundation.metaplex.rpc.serializers

import diglol.crypto.Hash
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

/**
 * A serializer class that serializes and deserializes data with a discriminator.
 *
 * @param discriminator A ByteArray representing the discriminator.
 * @param serializer The Kotlin serializer for the data to be serialized/deserialized.
 */
open class DiscriminatorSerializer<T>(val discriminator: ByteArray, serializer: KSerializer<T>)
    : KSerializer<T> {

    private val accountSerializer = serializer
    override val descriptor: SerialDescriptor = accountSerializer.descriptor

    override fun serialize(encoder: Encoder, value: T) {
        discriminator.forEach { encoder.encodeByte(it) }
        accountSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): T {
        ByteArray(discriminator.size).map { decoder.decodeByte() }.apply {
            // should we/how can we check that the discriminator is correct?
//            check(discriminator contentEquals this.toByteArray()) {
//                "The decoded discriminant (${this.toByteArray().contentToString()}) differed from " +
//                        "the expected discriminant (${discriminator.contentToString()})."
//            }
        }
        return accountSerializer.deserialize(decoder)
    }
}

/**
 * An inline function for creating ByteDiscriminatorSerializer instances.
 *
 * @param discriminator A Byte representing the discriminator.
 * @return A ByteDiscriminatorSerializer instance.
 */
inline fun <reified A> ByteDiscriminatorSerializer(discriminator: Byte) =
    ByteDiscriminatorSerializer<A>(discriminator, serializer())

/**
 * An open class that specializes DiscriminatorSerializer for byte-based discriminators.
 *
 * @param discriminator A Byte representing the discriminator.
 * @param serializer The Kotlin serializer for the data to be serialized/deserialized.
 */
open class ByteDiscriminatorSerializer<T>(discriminator: Byte, serializer: KSerializer<T>)
    : DiscriminatorSerializer<T>(byteArrayOf(discriminator), serializer)

/**
 * An open class that specializes DiscriminatorSerializer for anchor-based discriminators.
 *
 * The discriminator is defined by the first 8 bytes of the Sha256 hash of the account's
 * Rust identifier --i.e., the struct type name-- and ensures no account can be substituted for
 * another. It lets Anchor know what type of account it should deserialize the data as.
 *
 * @param namespace A String representing the namespace.
 * @param ixName A String representing the ixName.
 * @param serializer The Kotlin serializer for the data to be serialized/deserialized.
 */
open class AnchorDiscriminatorSerializer<T>(namespace: String, ixName: String,
                                            serializer: KSerializer<T>
)
    : DiscriminatorSerializer<T>(buildDiscriminator(namespace, ixName), serializer) {
    companion object {
        fun buildDiscriminator(namespace: String, ixName: String) = runBlocking {
            Hash(Hash.Type.SHA256)
                .hash("$namespace:$ixName".encodeToByteArray())
                .sliceArray(0 until 8)
        }
    }
}

/**
 * A class that specializes AnchorDiscriminatorSerializer for account serialization.
 *
 * @param accountName A String representing the account name.
 * @param serializer The Kotlin serializer for the data to be serialized/deserialized.
 */
class AnchorAccountSerializer<T>(accountName: String, serializer: KSerializer<T>)
    : AnchorDiscriminatorSerializer<T>("account", accountName, serializer)

/**
 * An inline function for creating AnchorAccountSerializer instances.
 *
 * @param accountName A String representing the account name.
 * @return An AnchorAccountSerializer instance.
 */
inline fun <reified A> AnchorAccountSerializer(accountName: String = A::class.simpleName ?: "") =
    AnchorAccountSerializer<A>(accountName, serializer())

/**
 * A class that specializes AnchorDiscriminatorSerializer for instruction serialization.
 *
 * @param ixName A String representing the instruction name.
 * @param serializer The Kotlin serializer for the data to be serialized/deserialized.
 */
class AnchorInstructionSerializer<T>(ixName: String, serializer: KSerializer<T>)
    : AnchorDiscriminatorSerializer<T>("global", ixName, serializer)

/**
 * An inline function for creating AnchorInstructionSerializer instances.
 *
 * @param ixName A String representing the instruction name.
 * @return An AnchorInstructionSerializer instance.
 */
inline fun <reified A> AnchorInstructionSerializer(ixName: String) =
    AnchorInstructionSerializer<A>(ixName, serializer())