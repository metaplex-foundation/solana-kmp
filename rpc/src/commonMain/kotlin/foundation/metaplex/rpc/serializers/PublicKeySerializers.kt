/*
 * PublicKeySerializers
 * Metaplex
 * 
 * Created by Funkatronics on 7/20/2022
 */

package foundation.metaplex.rpc.serializers

import com.funkatronics.kborsh.BorshDecoder
import com.funkatronics.kborsh.BorshEncoder
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * This object provides a serializer for serializing and deserializing PublicKey objects.
 * It is designed to handle PublicKey objects represented as 32-byte arrays.
 */
object PublicKeyAs32ByteSerializer : KSerializer<PublicKey> {

    /**
     * Gets the descriptor for this serializer.
     *
     * @return The serial descriptor for PublicKey.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PublicKey")

    /**
     * Serializes a PublicKey object into the specified encoder.
     *
     * @param encoder The encoder used for serialization.
     * @param value The PublicKey object to be serialized.
     */
    override fun serialize(encoder: Encoder, value: PublicKey) =
        if (encoder is BorshEncoder) value.toByteArray().forEach { b -> encoder.encodeByte(b) }
        else encoder.encodeSerializableValue(ByteArraySerializer(), value.toByteArray())

    /**
     * Deserializes a PublicKey object from the specified decoder.
     *
     * @param decoder The decoder used for deserialization.
     * @return The deserialized PublicKey object.
     */
    override fun deserialize(decoder: Decoder): PublicKey =
        if (decoder is BorshDecoder) PublicKey((0 until 32).map { decoder.decodeByte() }.toByteArray())
        else PublicKey(decoder.decodeSerializableValue(ByteArraySerializer()))
}

/**
 * This object provides a serializer for serializing and deserializing PublicKey objects as strings.
 * It converts a PublicKey object to its string representation for serialization and vice versa for deserialization.
 */
object PublicKeyAsStringSerializer : KSerializer<PublicKey> {
    /**
     * Gets the descriptor for this serializer.
     *
     * @return The serial descriptor for PublicKey.
     */
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PublicKey")

    /**
     * Serializes a PublicKey object into a string representation using the specified encoder.
     *
     * @param encoder The encoder used for serialization.
     * @param value The PublicKey object to be serialized.
     */
    override fun serialize(encoder: Encoder, value: PublicKey) = encoder.encodeString(value.toString())

    /**
     * Deserializes a PublicKey object from a string representation using the specified decoder.
     *
     * @param decoder The decoder used for deserialization.
     * @return The deserialized PublicKey object.
     * @throws SerializationException if the input string cannot be parsed as a PublicKey.
     */
    override fun deserialize(decoder: Decoder): PublicKey = decoder.decodeString().let {
        if (it.isEmpty()) throw SerializationException("received empty public key")
        PublicKey(it)
    }
}