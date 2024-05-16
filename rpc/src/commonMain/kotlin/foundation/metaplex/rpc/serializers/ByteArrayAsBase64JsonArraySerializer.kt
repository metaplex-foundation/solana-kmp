package foundation.metaplex.rpc.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * This object provides a serializer for encoding and decoding byte arrays as Base64-encoded JSON arrays.
 * output = {
 *      [
 *          "theBase64EncodedString",
 *          "base64"
 *      ]
 * }
 */
object ByteArrayAsBase64JsonArraySerializer: KSerializer<ByteArray> {
    private val delegateSerializer = ListSerializer(String.serializer())
    override val descriptor: SerialDescriptor = delegateSerializer.descriptor

    /**
     * Serializes the provided byte array into a Base64-encoded JSON array representation.
     *
     * @param encoder The encoder used for serialization.
     * @param value The byte array to be serialized.
     */
    @OptIn(ExperimentalEncodingApi::class)
    override fun serialize(encoder: Encoder, value: ByteArray) =
        encoder.encodeSerializableValue(
            delegateSerializer, listOf(
                Base64.encode(value), "base64"
            ))

    /**
     * Deserializes a Base64-encoded JSON array representation into a byte array.
     *
     * @param decoder The decoder used for deserialization.
     * @return The deserialized byte array.
     * @throws SerializationException if the input is not a valid Base64-encoded JSON array.
     */
    @OptIn(ExperimentalEncodingApi::class)
    override fun deserialize(decoder: Decoder): ByteArray {
        decoder.decodeSerializableValue(delegateSerializer).apply {
            if (contains("base64")) first { it != "base64" }.apply {
                return Base64.decode(this)
            }
            else throw(SerializationException("Not Base64"))
        }
    }
}

