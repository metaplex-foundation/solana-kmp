//
// Types
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-09-11
//
@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mplbubblegum.generated.splaccountcompression

import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.ByteArray
import kotlin.UByte
import kotlin.UInt
import kotlin.ULong
import kotlin.collections.List
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ApplicationDataEventV1(val applicationData: ByteArray)

@Serializable
data class ChangeLogEventV1(
    val id: PublicKey,
    val path: List<PathNode>,
    val seq: ULong,
    val index: UInt
)

@Serializable
data class ConcurrentMerkleTreeHeader(val accountType: CompressionAccountType, val header:
        ConcurrentMerkleTreeHeaderData)

@Serializable
data class ConcurrentMerkleTreeHeaderDataV1(
    val maxBufferSize: UInt,
    val maxDepth: UInt,
    val authority: PublicKey,
    val creationSlot: ULong,
    val padding: List<UByte>
)

@Serializable
data class PathNode(val node: List<UByte>, val index: UInt)

@Serializable(with = ApplicationDataEventSerializer::class)
sealed class ApplicationDataEvent {
    data class V1(val applicationdataeventv1: ApplicationDataEventV1) : ApplicationDataEvent()
}

class ApplicationDataEventSerializer : KSerializer<ApplicationDataEvent> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ApplicationDataEvent) {
        when(value){ 
           is ApplicationDataEvent.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(ApplicationDataEventV1.serializer(),
                value.applicationdataeventv1)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): ApplicationDataEvent =
            when(decoder.decodeByte().toInt()){
       0 -> ApplicationDataEvent.V1 (
           applicationdataeventv1 =
            decoder.decodeSerializableValue(ApplicationDataEventV1.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = ChangeLogEventSerializer::class)
sealed class ChangeLogEvent {
    data class V1(val changelogeventv1: ChangeLogEventV1) : ChangeLogEvent()
}

class ChangeLogEventSerializer : KSerializer<ChangeLogEvent> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ChangeLogEvent) {
        when(value){ 
           is ChangeLogEvent.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(ChangeLogEventV1.serializer(),
                value.changelogeventv1)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): ChangeLogEvent = when(decoder.decodeByte().toInt()){
       0 -> ChangeLogEvent.V1 (
           changelogeventv1 = decoder.decodeSerializableValue(ChangeLogEventV1.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = AccountCompressionEventSerializer::class)
sealed class AccountCompressionEvent {
    data class ChangeLog(val changelogevent: ChangeLogEvent) : AccountCompressionEvent()

    data class ApplicationData(val applicationdataevent: ApplicationDataEvent) :
            AccountCompressionEvent()
}

class AccountCompressionEventSerializer : KSerializer<AccountCompressionEvent> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: AccountCompressionEvent) {
        when(value){ 
           is AccountCompressionEvent.ChangeLog -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(ChangeLogEvent.serializer(), value.changelogevent)
           }
           is AccountCompressionEvent.ApplicationData -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(ApplicationDataEvent.serializer(),
                value.applicationdataevent)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): AccountCompressionEvent =
            when(decoder.decodeByte().toInt()){
       0 -> AccountCompressionEvent.ChangeLog (
           changelogevent = decoder.decodeSerializableValue(ChangeLogEvent.serializer()),
     )   1 -> AccountCompressionEvent.ApplicationData (
           applicationdataevent =
            decoder.decodeSerializableValue(ApplicationDataEvent.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class CompressionAccountType {
    Uninitialized,

    ConcurrentMerkleTree
}

@Serializable(with = ConcurrentMerkleTreeHeaderDataSerializer::class)
sealed class ConcurrentMerkleTreeHeaderData {
    data class V1(val concurrentmerkletreeheaderdatav1: ConcurrentMerkleTreeHeaderDataV1) :
            ConcurrentMerkleTreeHeaderData()
}

class ConcurrentMerkleTreeHeaderDataSerializer : KSerializer<ConcurrentMerkleTreeHeaderData> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ConcurrentMerkleTreeHeaderData) {
        when(value){ 
           is ConcurrentMerkleTreeHeaderData.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(ConcurrentMerkleTreeHeaderDataV1.serializer(),
                value.concurrentmerkletreeheaderdatav1)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): ConcurrentMerkleTreeHeaderData =
            when(decoder.decodeByte().toInt()){
       0 -> ConcurrentMerkleTreeHeaderData.V1 (
           concurrentmerkletreeheaderdatav1 =
            decoder.decodeSerializableValue(ConcurrentMerkleTreeHeaderDataV1.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}
