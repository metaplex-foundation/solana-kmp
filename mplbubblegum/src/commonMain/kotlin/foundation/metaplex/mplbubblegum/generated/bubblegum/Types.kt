@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mplbubblegum.generated.bubblegum

import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.Boolean
import kotlin.String
import kotlin.UByte
import kotlin.ULong
import kotlin.UShort
import kotlin.collections.List
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Creator(
    val address: PublicKey,
    val verified: Boolean,
    val share: UByte
)

@Serializable
data class Uses(
    val useMethod: UseMethod,
    val remaining: ULong,
    val total: ULong
)

@Serializable
data class Collection(val verified: Boolean, val key: PublicKey)

@Serializable
data class MetadataArgs(
    val name: String,
    val symbol: String,
    val uri: String,
    val sellerFeeBasisPoints: UShort,
    val primarySaleHappened: Boolean,
    val isMutable: Boolean,
    val editionNonce: UByte?,
    val tokenStandard: TokenStandard?,
    val collection: Collection?,
    val uses: Uses?,
    val tokenProgramVersion: TokenProgramVersion,
    val creators: List<Creator>
)

@Serializable
enum class Version {
    V1
}

@Serializable(with = LeafSchemaSerializer::class)
sealed class LeafSchema {
    data class V1(
        val id: PublicKey,
        val owner: PublicKey,
        val delegate: PublicKey,
        val nonce: ULong,
        val data_hash: List<UByte>,
        val creator_hash: List<UByte>
    ) : LeafSchema()
}

class LeafSchemaSerializer : KSerializer<LeafSchema> {
    override val descriptor: SerialDescriptor =
        kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: LeafSchema) {
        when(value){
            is LeafSchema.V1 -> {
                encoder.encodeSerializableValue(Byte.serializer(), 0.toByte())

                encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.id)
                encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.owner)
                encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.delegate)
                encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.nonce)
                encoder.encodeSerializableValue(ListSerializer(kotlin.UByte.serializer()),
                    value.data_hash)
                encoder.encodeSerializableValue(ListSerializer(kotlin.UByte.serializer()),
                    value.creator_hash)
            }
            else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): LeafSchema = when(decoder.decodeByte().toInt()){
        0 -> LeafSchema.V1 (
            id = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
            owner = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
            delegate = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
            nonce = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
            data_hash = decoder.decodeSerializableValue(ListSerializer(kotlin.UByte.serializer())),
            creator_hash =
            decoder.decodeSerializableValue(ListSerializer(kotlin.UByte.serializer())),
        )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class TokenProgramVersion {
    Original,

    Token2022
}

@Serializable
enum class TokenStandard {
    NonFungible,

    FungibleAsset,

    Fungible,

    NonFungibleEdition
}

@Serializable
enum class UseMethod {
    Burn,

    Multiple,

    Single
}

@Serializable
enum class BubblegumEventType {
    Uninitialized,

    LeafSchemaEvent
}

@Serializable
enum class DecompressableState {
    Enabled,

    Disabled
}

@Serializable
enum class InstructionName {
    Unknown,

    MintV1,

    Redeem,

    CancelRedeem,

    Transfer,

    Delegate,

    DecompressV1,

    Compress,

    Burn,

    CreateTree,

    VerifyCreator,

    UnverifyCreator,

    VerifyCollection,

    UnverifyCollection,

    SetAndVerifyCollection,

    MintToCollectionV1,

    SetDecompressableState
}
