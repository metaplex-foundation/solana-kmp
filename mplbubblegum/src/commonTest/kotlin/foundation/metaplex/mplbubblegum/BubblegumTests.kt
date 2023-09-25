@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mplbubblegum

import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.funkatronics.kborsh.BorshDecoder
import com.funkatronics.kborsh.BorshEncoder
import com.metaplex.signer.Signer
import foundation.metaplex.base58.decodeBase58
import foundation.metaplex.mplbubblegum.generated.bubblegum.BubblegumInstructions
import foundation.metaplex.mplbubblegum.generated.bubblegum.DecompressableState
import foundation.metaplex.mplbubblegum.generated.bubblegum.MetadataArgs
import foundation.metaplex.mplbubblegum.generated.bubblegum.TokenProgramVersion
import foundation.metaplex.mplbubblegum.generated.bubblegum.TokenStandard
import foundation.metaplex.mplbubblegum.generated.bubblegum.TreeConfig
import foundation.metaplex.mplbubblegum.system.SystemProgram
import foundation.metaplex.mplbubblegum.system.SystemProgram.PROGRAM_ID
import foundation.metaplex.rpc.RPC
import foundation.metaplex.rpc.serializers.AnchorAccountSerializer
import foundation.metaplex.rpc.serializers.BorshAsBase64JsonArraySerializer
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solana.transactions.SolanaTransactionBuilder
import foundation.metaplex.solanaeddsa.Keypair
import foundation.metaplex.solanaeddsa.SolanaEddsa
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val rpcUrl = "https://api.devnet.solana.com/"
val rpc = RPC(rpcUrl)

class SolanaKeypair(
    override val publicKey: PublicKey,
    override val secretKey: ByteArray
) : Keypair

class HotSigner(private val keyPair: Keypair) : Signer {
    override val publicKey: PublicKey = keyPair.publicKey
    override suspend fun signMessage(message: ByteArray): ByteArray = SolanaEddsa.sign(message, keyPair)
}


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

class BubblegumTest {

    companion object {

        suspend fun signer(): HotSigner {
            val privateKey = "4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs".decodeBase58().copyOfRange(0, 32)
            val k = SolanaEddsa.createKeypairFromSecretKey(privateKey)
            return HotSigner(SolanaKeypair(k.publicKey, k.secretKey))
        }
    }

    @Test
    fun testCreateTree() = runTest {
        val blockhash = rpc.getLatestBlockhash(null)
        val lamports = rpc.getMinimumBalanceForRentExemption(31800u) // RPC get minRent

        val merkleTreeKeypair = SolanaEddsa.generateKeypair()
        val merkleTree = HotSigner(SolanaKeypair(merkleTreeKeypair.publicKey, merkleTreeKeypair.secretKey))

        val treeAuthority = SolanaEddsa.findPda(
            PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            arrayOf(merkleTree.publicKey.toByteArray())
        )

        val tree = SolanaTransactionBuilder()
            .addInstruction(
                SystemProgram.createAccount(
                    signer().publicKey,
                    merkleTree.publicKey,
                    lamports.toLong(),
                    31800u.toLong(),
                    PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK")
                )
            )
            .addInstruction(
                BubblegumInstructions.createTree(
                    treeAuthority.address,
                    merkleTree.publicKey,
                    signer().publicKey,
                    signer().publicKey,
                    PublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                    PROGRAM_ID,
                    14u,
                    64u,
                    false
                )
            )
            .setRecentBlockHash(blockhash.blockhash)
            .setSigners(listOf(signer(), merkleTree))
            .build()

        println(treeAuthority.address)
        val serializedTransaction = tree.serialize()
        val signature = rpc.sendTransaction(serializedTransaction, null)
        assertNotNull(signature)
    }

    @Test
    fun testFetchTreeConfig()= runTest {
        val treeConfig = rpc.getAccountInfo(PublicKey("CYqvyie3xPueVu4WuoRMWW2hQHgmKfEHgvpssr52CpTo"), null,
            BorshAsBase64JsonArraySerializer(AnchorAccountSerializer("TreeConfig", TreeConfig.serializer()))
        )
        assertNotNull(treeConfig)
        assertEquals(treeConfig.data!!.treeDelegate, PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"))
        assertEquals(treeConfig.data!!.treeCreator, PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"))
        assertEquals(treeConfig.data!!.isPublic, false)
        assertEquals(treeConfig.data!!.totalMintCapacity, 16384u)
        assertEquals(treeConfig.data!!.numMinted, 0u)
        assertEquals(treeConfig.data!!.isDecompressable, DecompressableState.Disabled)
    }

    @Test
    fun testFetchEmptyMerkleTree()= runTest {
        val merkleTree = rpc.getAccountInfo(PublicKey("CxYcCxbSP6JGTg6y22hh8cx5Mpm6UCmxKi8SuzFXKAb1"), null,
            BorshAsBase64JsonArraySerializer( MerkleTree.serializer())
        )
        assertNotNull(merkleTree)
        assertEquals(merkleTree.data!!.discriminator, CompressionAccountType.ConcurrentMerkleTree)
        assertEquals(merkleTree.data!!.treeHeader.maxBufferSize, 64u)
        assertEquals(merkleTree.data!!.treeHeader.maxDepth, 14u)
        assertEquals(merkleTree.data!!.treeHeader.authority, PublicKey("BhtGND8r6VjjXnxQyVpi4av1hcaX5cPEU6f22G7W8cKT"))
        assertEquals(merkleTree.data!!.treeHeader.creationSlot, 244081182UL)
        assertEquals(merkleTree.data!!.treeHeader.paddingA, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingB, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingC, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingD, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingE, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingF, 0)

        assertEquals(merkleTree.data!!.tree.sequenceNumber, 0u)
        assertEquals(merkleTree.data!!.tree.activeIndex, 0u)
        assertEquals(merkleTree.data!!.tree.bufferSize, 1u)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().root, PublicKey("7DiCkBhs5HQLPEsKY6EjfNd3oBswnfRk9UAZcHqczL7m"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.size, 1)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.first(), PublicKey("11111111111111111111111111111111"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().index, 0u)

        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.size, 1)
        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.first(), PublicKey("91QHiAzosmGSwqG6CBE8cKz1LgLu5eszKoqmnBGMUa4g"))
        assertEquals(merkleTree.data!!.tree.rightMostPath.index, 0u)
    }

    @Test
    fun testFetchMerkleTreeWithMint()= runTest {
        val merkleTree = rpc.getAccountInfo(PublicKey("ALgwuCkX8EKiNR8Hr6Zpay6zvkHnFDu1spWS7R3qR1y1"), null,
            BorshAsBase64JsonArraySerializer( MerkleTree.serializer())
        )
        assertNotNull(merkleTree)
        assertEquals(merkleTree.data!!.discriminator, CompressionAccountType.ConcurrentMerkleTree)
        assertEquals(merkleTree.data!!.treeHeader.maxBufferSize, 64u)
        assertEquals(merkleTree.data!!.treeHeader.maxDepth, 14u)
        assertEquals(merkleTree.data!!.treeHeader.authority, PublicKey("6ifW4aVxR7B1QvrYD3WyEDsuzYvkU4mWo3GGdTbYtnb4"))
        assertEquals(merkleTree.data!!.treeHeader.creationSlot, 244284165UL)
        assertEquals(merkleTree.data!!.treeHeader.paddingA, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingB, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingC, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingD, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingE, 0)
        assertEquals(merkleTree.data!!.treeHeader.paddingF, 0)

        assertEquals(merkleTree.data!!.tree.sequenceNumber, 1u)
        assertEquals(merkleTree.data!!.tree.activeIndex, 1u)
        assertEquals(merkleTree.data!!.tree.bufferSize, 2u)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().root, PublicKey("7DiCkBhs5HQLPEsKY6EjfNd3oBswnfRk9UAZcHqczL7m"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.size, 2)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.first(), PublicKey("11111111111111111111111111111111"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().index, 0u)

        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.size, 2)
        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.first(), PublicKey("s6AF7Jimxi6f2FDq7KNr3Sfmmg2pdWNyMbYHcCj644X"))
        assertEquals(merkleTree.data!!.tree.rightMostPath.index, 0u)
    }

    @Test
    fun testMintV1() = runTest {
        val blockhash = rpc.getLatestBlockhash(null)
        val lamports = rpc.getMinimumBalanceForRentExemption(31800u) // RPC get minRent

        val merkleTreeKeypair = SolanaEddsa.generateKeypair()
        val merkleTree = HotSigner(SolanaKeypair(merkleTreeKeypair.publicKey, merkleTreeKeypair.secretKey))

        val treeAuthority = SolanaEddsa.findPda(
            PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            arrayOf(merkleTree.publicKey.toByteArray())
        )

        val metadata = MetadataArgs(
            name = "My NFT",
            symbol= "",
            uri = "https://example.com/my-nft.json",
            sellerFeeBasisPoints= 500u, // 5%
            collection= null,
            primarySaleHappened= false,
            isMutable= false,
            editionNonce= null,
            tokenStandard= TokenStandard.NonFungible,
            uses= null,
            tokenProgramVersion= TokenProgramVersion.Original,
            creators= listOf(),
        )

        val tree = SolanaTransactionBuilder()
            .addInstruction(
                SystemProgram.createAccount(
                    signer().publicKey,
                    merkleTree.publicKey,
                    lamports.toLong(),
                    31800u.toLong(),
                    PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK")
                )
            )
            .addInstruction(
                BubblegumInstructions.createTree(
                    treeAuthority.address,
                    merkleTree.publicKey,
                    signer().publicKey,
                    signer().publicKey,
                    PublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                    PROGRAM_ID,
                    14u,
                    64u,
                    false
                )
            )
            .addInstruction(
                BubblegumInstructions.mintV1(
                    treeAuthority.address,
                    signer().publicKey,
                    signer().publicKey,
                    merkleTree.publicKey,
                    signer().publicKey,
                    signer().publicKey,
                    PublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                    PROGRAM_ID,
                    metadata
                )
            )
            .setRecentBlockHash(blockhash.blockhash)
            .setSigners(listOf(signer(), merkleTree))
            .build()

        val serializedTransaction = tree.serialize()
        val signature = rpc.sendTransaction(serializedTransaction, null)
        assertNotNull(signature)
        println(merkleTree.publicKey)
    }
}