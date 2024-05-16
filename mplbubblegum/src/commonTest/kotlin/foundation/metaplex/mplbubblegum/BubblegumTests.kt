
package foundation.metaplex.mplbubblegum

import com.metaplex.signer.Signer
import com.solana.publickey.PublicKey
import com.solana.publickey.SolanaPublicKey
import foundation.metaplex.base58.decodeBase58
import foundation.metaplex.mplbubblegum.generated.bubblegum.BubblegumInstructions
import foundation.metaplex.mplbubblegum.generated.bubblegum.DecompressableState
import foundation.metaplex.mplbubblegum.generated.bubblegum.MetadataArgs
import foundation.metaplex.mplbubblegum.generated.bubblegum.TokenProgramVersion
import foundation.metaplex.mplbubblegum.generated.bubblegum.TokenStandard
import foundation.metaplex.mplbubblegum.generated.bubblegum.TreeConfig
import foundation.metaplex.mplbubblegum.generated.bubblegum.hook.CompressionAccountType
import foundation.metaplex.mplbubblegum.generated.bubblegum.hook.MerkleTree
import foundation.metaplex.solana.programs.SystemProgram
import foundation.metaplex.solana.programs.SystemProgram.PROGRAM_ID
import foundation.metaplex.rpc.RPC
import foundation.metaplex.rpc.serializers.AnchorAccountSerializer
import foundation.metaplex.rpc.serializers.BorshAsBase64JsonArraySerializer
import foundation.metaplex.solana.transactions.SolanaTransactionBuilder
import foundation.metaplex.solanaeddsa.Keypair
import foundation.metaplex.solanaeddsa.SolanaEddsa
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val rpcUrl = "https://api.devnet.solana.com/"
val rpc = RPC(rpcUrl)

fun SolanaPublicKey(base58: String) = SolanaPublicKey.from(base58)

class SolanaKeypair(
    override val publicKey: PublicKey,
    override val secretKey: ByteArray
) : Keypair

class HotSigner(private val keyPair: Keypair) : Signer {
    override val publicKey = keyPair.publicKey
    override suspend fun signMessage(message: ByteArray): ByteArray = SolanaEddsa.sign(message, keyPair)
}




class BubblegumIntegTest {

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
            SolanaPublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            arrayOf(merkleTree.publicKey.bytes)
        )

        val tree = SolanaTransactionBuilder()
            .addInstruction(
                SystemProgram.createAccount(
                    signer().publicKey,
                    merkleTree.publicKey,
                    lamports.toLong(),
                    31800u.toLong(),
                    SolanaPublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK")
                )
            )
            .addInstruction(
                BubblegumInstructions.createTree(
                    treeAuthority.address,
                    merkleTree.publicKey,
                    signer().publicKey,
                    signer().publicKey,
                    SolanaPublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    SolanaPublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
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
        val treeConfig = rpc.getAccountInfo(SolanaPublicKey("CYqvyie3xPueVu4WuoRMWW2hQHgmKfEHgvpssr52CpTo"), null,
            BorshAsBase64JsonArraySerializer(AnchorAccountSerializer("TreeConfig", TreeConfig.serializer()))
        )
        assertNotNull(treeConfig)
        assertEquals(treeConfig.data!!.treeDelegate, SolanaPublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"))
        assertEquals(treeConfig.data!!.treeCreator, SolanaPublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"))
        assertEquals(treeConfig.data!!.isPublic, false)
        assertEquals(treeConfig.data!!.totalMintCapacity, 16384u)
        assertEquals(treeConfig.data!!.numMinted, 0u)
        assertEquals(treeConfig.data!!.isDecompressable, DecompressableState.Disabled)
    }

    @Test
    fun testFetchEmptyMerkleTree()= runTest {
        val merkleTree = rpc.getAccountInfo(SolanaPublicKey("CxYcCxbSP6JGTg6y22hh8cx5Mpm6UCmxKi8SuzFXKAb1"), null,
            BorshAsBase64JsonArraySerializer( MerkleTree.serializer())
        )
        assertNotNull(merkleTree)
        assertEquals(merkleTree.data!!.discriminator, CompressionAccountType.ConcurrentMerkleTree)
        assertEquals(merkleTree.data!!.treeHeader.maxBufferSize, 64u)
        assertEquals(merkleTree.data!!.treeHeader.maxDepth, 14u)
        assertEquals(merkleTree.data!!.treeHeader.authority, SolanaPublicKey("BhtGND8r6VjjXnxQyVpi4av1hcaX5cPEU6f22G7W8cKT"))
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
        assertEquals(merkleTree.data!!.tree.changeLogs.first().root, SolanaPublicKey("7DiCkBhs5HQLPEsKY6EjfNd3oBswnfRk9UAZcHqczL7m"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.size, 1)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.first(), SolanaPublicKey("11111111111111111111111111111111"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().index, 0u)

        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.size, 1)
        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.first(), SolanaPublicKey("91QHiAzosmGSwqG6CBE8cKz1LgLu5eszKoqmnBGMUa4g"))
        assertEquals(merkleTree.data!!.tree.rightMostPath.index, 0u)
    }

    @Test
    fun testFetchMerkleTreeWithMint()= runTest {
        val merkleTree = rpc.getAccountInfo(SolanaPublicKey("ALgwuCkX8EKiNR8Hr6Zpay6zvkHnFDu1spWS7R3qR1y1"), null,
            BorshAsBase64JsonArraySerializer( MerkleTree.serializer())
        )
        assertNotNull(merkleTree)
        assertEquals(merkleTree.data!!.discriminator, CompressionAccountType.ConcurrentMerkleTree)
        assertEquals(merkleTree.data!!.treeHeader.maxBufferSize, 64u)
        assertEquals(merkleTree.data!!.treeHeader.maxDepth, 14u)
        assertEquals(merkleTree.data!!.treeHeader.authority, SolanaPublicKey("6ifW4aVxR7B1QvrYD3WyEDsuzYvkU4mWo3GGdTbYtnb4"))
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
        assertEquals(merkleTree.data!!.tree.changeLogs.first().root, SolanaPublicKey("7DiCkBhs5HQLPEsKY6EjfNd3oBswnfRk9UAZcHqczL7m"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.size, 2)
        assertEquals(merkleTree.data!!.tree.changeLogs.first().pathNodes.first(), SolanaPublicKey("11111111111111111111111111111111"))
        assertEquals(merkleTree.data!!.tree.changeLogs.first().index, 0u)

        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.size, 2)
        assertEquals(merkleTree.data!!.tree.rightMostPath.proof.first(), SolanaPublicKey("s6AF7Jimxi6f2FDq7KNr3Sfmmg2pdWNyMbYHcCj644X"))
        assertEquals(merkleTree.data!!.tree.rightMostPath.index, 0u)
    }

    @Test
    fun testMintV1() = runTest {
        val blockhash = rpc.getLatestBlockhash(null)
        val lamports = rpc.getMinimumBalanceForRentExemption(31800u) // RPC get minRent

        val merkleTreeKeypair = SolanaEddsa.generateKeypair()
        val merkleTree = HotSigner(SolanaKeypair(merkleTreeKeypair.publicKey, merkleTreeKeypair.secretKey))

        val treeAuthority = SolanaEddsa.findPda(
            SolanaPublicKey.from("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            arrayOf(merkleTree.publicKey.bytes)
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
                    SolanaPublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK")
                )
            )
            .addInstruction(
                BubblegumInstructions.createTree(
                    treeAuthority.address,
                    merkleTree.publicKey,
                    signer().publicKey,
                    signer().publicKey,
                    SolanaPublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    SolanaPublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
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
                    SolanaPublicKey("noopb9bkMVfRPU8AsbpTUg8AQkHtKwMYZiFUjNRtMmV"),
                    SolanaPublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
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
