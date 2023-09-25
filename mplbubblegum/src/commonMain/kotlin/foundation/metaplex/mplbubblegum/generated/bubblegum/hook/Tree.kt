@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mplbubblegum.generated.bubblegum.hook

import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.funkatronics.kborsh.BorshDecoder
import com.funkatronics.kborsh.BorshEncoder
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
class Path(
    val proof: List<PublicKey>,
    val leaf: PublicKey,
    val index: UInt,
    val padding: UInt
)
class PathBorshSerializer(val maxDepth: ULong) : KSerializer<Path> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Path")
    override fun serialize(encoder: Encoder, value: Path) {
        if (encoder is BorshEncoder) {
            for (pk in value.proof){
                encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, pk)
            }
            encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.leaf)

            val buffer = PlatformBuffer.allocate(32)
            buffer.writeUInt(value.index)
            val fixedSlicedUIndex = buffer.readUnsignedInt()
            encoder.encodeInt(fixedSlicedUIndex.toInt())
            encoder.encodeInt(0)
        } else {
            throw Throwable("Use Path.serialer()")
        }
    }
    override fun deserialize(decoder: Decoder): Path {
        if (decoder is BorshDecoder){
            val proof = mutableListOf<PublicKey>()
            for (pk in 0 until this.maxDepth.toInt()){
                proof.add(decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer))
            }
            val leaf = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer)
            val index = decoder.decodeInt()

            val buffer = PlatformBuffer.allocate(32)
            buffer.writeInt(index)
            val fixedSlicedUIndex = buffer.readByte()
            return Path(proof, leaf, fixedSlicedUIndex.toUInt(),0u)
        } else {
            throw Throwable("Use Path.serialer()")
        }
    }
}

@Serializable
class ChangeLog(
    val root: PublicKey,
    val pathNodes: List<PublicKey>,
    val index: UInt
)

class ChangeLogBorshSerializer(val maxDepth: ULong) : KSerializer<ChangeLog> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Path")
    override fun serialize(encoder: Encoder, value: ChangeLog) {
        if (encoder is BorshEncoder) {
            encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.root)
            for (pk in value.pathNodes){
                encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, pk)
            }
            val buffer = PlatformBuffer.allocate(32)
            buffer.writeUInt(value.index)
            val fixedSlicedIndex = buffer.readByte()
            encoder.encodeByte(fixedSlicedIndex)
        } else {
            throw Throwable("Use ChangeLog.serializer()")
        }
    }
    override fun deserialize(decoder: Decoder): ChangeLog {
        if (decoder is BorshDecoder){
            val root = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer)
            val pathNodes = mutableListOf<PublicKey>()
            for (pk in 0 until this.maxDepth.toInt()){
                pathNodes.add(decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer))
            }
            val index = decoder.decodeInt()
            val buffer = PlatformBuffer.allocate(32)
            buffer.writeInt(index)
            val fixedSlicedIndex = buffer.readUnsignedByte().toUInt()
            return ChangeLog(root, pathNodes, fixedSlicedIndex)
        } else {
            throw Throwable("Use Path.serializer()")
        }
    }
}

@Serializable(with = ConcurrentMerkleTreeSerializer::class)
class ConcurrentMerkleTree(
    val sequenceNumber: ULong,
    val activeIndex: ULong,
    val bufferSize: ULong,
    val changeLogs: List<ChangeLog>,
    val rightMostPath: Path,
)

object ConcurrentMerkleTreeSerializer : KSerializer<ConcurrentMerkleTree> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ConcurrentMerkleTree")
    override fun serialize(encoder: Encoder, value: ConcurrentMerkleTree) {
        if (encoder is BorshEncoder) {
            encoder.encodeLong(value.sequenceNumber.toLong())
            encoder.encodeLong(value.activeIndex.toLong())
            encoder.encodeLong(value.bufferSize.toLong())
            for (changeLog in value.changeLogs){
                encoder.encodeSerializableValue(ChangeLogBorshSerializer(value.bufferSize), changeLog)
            }
            encoder.encodeSerializableValue(PathBorshSerializer(value.bufferSize), value.rightMostPath)
        } else {
            throw Throwable("Use ConcurrentMerkleTree.serializer()")
        }
    }
    override fun deserialize(decoder: Decoder): ConcurrentMerkleTree {
        if (decoder is BorshDecoder){
            val sequenceNumber = decoder.decodeLong().toULong()
            val activeIndex = decoder.decodeLong().toULong()
            val bufferSize = decoder.decodeLong().toULong()
            val changeLogs = mutableListOf<ChangeLog>()
            for (index in 0 until bufferSize.toInt()){
                changeLogs.add(decoder.decodeSerializableValue(ChangeLogBorshSerializer(bufferSize)))
            }
            val rightMostPath = decoder.decodeSerializableValue(PathBorshSerializer(bufferSize))
            return ConcurrentMerkleTree(sequenceNumber, activeIndex, bufferSize, changeLogs, rightMostPath)
        } else {
            throw Throwable("Use ConcurrentMerkleTree.serializer()")
        }
    }
}

@Serializable
class ConcurrentMerkleTreeHeaderData(
    val version: Byte,
    val maxBufferSize: UInt,
    val maxDepth: UInt,
    val authority: PublicKey,
    val creationSlot: ULong,
    val paddingA: Byte,
    val paddingB: Byte,
    val paddingC: Byte,
    val paddingD: Byte,
    val paddingE: Byte,
    val paddingF: Byte,
)

@Serializable
enum class CompressionAccountType {
    Uninitialized,
    ConcurrentMerkleTree,
}

@Serializable
class MerkleTree (
    val discriminator: CompressionAccountType,
    val treeHeader: ConcurrentMerkleTreeHeaderData,
    val tree: ConcurrentMerkleTree,
)