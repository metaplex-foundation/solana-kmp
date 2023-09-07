package foundation.metaplex.rpc.serializers

import com.funkatronics.kborsh.BorshDecoder
import com.funkatronics.kborsh.BorshEncoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * This class provides a serializer for encoding and decoding data using the Borsh encoding scheme.
 * Decodes/Encodes input using the Borsh encoding scheme, and serializes it as a Base64 encoded
 * string, formatted as Json string array:
 * output = {
 *      [
 *          "theBorshEncodedBytesAsBase64String",
 *          "base64"
 *      ]
 * }
 *
 * @param T The type of data to be serialized and deserialized.
 * @param dataSerializer The Kotlin serialization serializer for the data of type T.
 */
class BorshAsBase64JsonArraySerializer<T>(private val dataSerializer: KSerializer<T>):
    KSerializer<T?> {
    private val delegateSerializer = ByteArrayAsBase64JsonArraySerializer
    override val descriptor: SerialDescriptor = dataSerializer.descriptor

    /**
     * Serializes the provided value of type T into a Borsh-encoded Base64 JSON array representation.
     *
     * @param encoder The encoder used for serialization.
     * @param value The value of type T to be serialized.
     */
    override fun serialize(encoder: Encoder, value: T?) =
        encoder.encodeSerializableValue(delegateSerializer,
            value?.let {
                BorshEncoder().apply {
                    encodeSerializableValue(dataSerializer, value)
                }.borshEncodedBytes
            } ?: byteArrayOf()
        )

    /**
     * Deserializes a Borsh-encoded Base64 JSON array representation into a value of type T.
     *
     * @param decoder The decoder used for deserialization.
     * @return The deserialized value of type T, or null if the input is empty.
     */
    override fun deserialize(decoder: Decoder): T? =
        decoder.decodeSerializableValue(delegateSerializer).run {
            if (this.isEmpty()) return null
            BorshDecoder(this).decodeSerializableValue(dataSerializer)
        }
}
