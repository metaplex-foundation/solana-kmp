package foundation.metaplex.rpc.serializers

import com.funkatronics.kborsh.BorshDecoder
import com.funkatronics.kborsh.BorshEncoder
import foundation.metaplex.amount.SolAmount
import foundation.metaplex.amount.lamports
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object SolAmountSerializer : KSerializer<SolAmount> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("SolAmount")

    override fun serialize(encoder: Encoder, value: SolAmount) =
        if (encoder is BorshEncoder) value.basisPoints.toByteArray().forEach { b -> encoder.encodeByte(b) }
        else encoder.encodeSerializableValue(ByteArraySerializer(), value.basisPoints.toByteArray())

    override fun deserialize(decoder: Decoder): SolAmount =
        if (decoder is BorshDecoder) lamports( decoder.decodeLong())
        else lamports(decoder.decodeSerializableValue(Long.serializer()))
}