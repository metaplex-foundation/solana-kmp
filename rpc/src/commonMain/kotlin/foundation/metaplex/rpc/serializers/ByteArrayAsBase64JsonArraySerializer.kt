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
 * (De)Serializes any array of bytes as a Base64 encoded string, formatted as Json string array:
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

    @OptIn(ExperimentalEncodingApi::class)
    override fun serialize(encoder: Encoder, value: ByteArray) =
        encoder.encodeSerializableValue(
            delegateSerializer, listOf(
                Base64.encode(value), "base64"
            ))

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

