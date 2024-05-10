package foundation.metaplex.rpc

import foundation.metaplex.rpc.serializers.BorshAsBase64JsonArraySerializer
import foundation.metaplex.rpc.serializers.Key
import foundation.metaplex.rpc.serializers.Metadata
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RpcIntegTests {
    private var rpcUrl: String = "https://api.mainnet-beta.solana.com"

    @Test
    fun testActualGetAccountInfo() = runTest {
        val randomPublicKey = PublicKey("9VHphpWFmUxVHxzWyeYJYYbQADWZ7X6PLzyWER8Lc3k2")
        val rpc = RPC(rpcUrl)
        val metadata = rpc.getAccountInfo(
            randomPublicKey,
            null,
            serializer = BorshAsBase64JsonArraySerializer(Metadata.serializer())
        )!!.data
        assertNotNull(metadata)
        assertEquals(metadata.key, Key.MetadataV1)
        assertEquals(metadata.data.name, "Gooberg #2235")
    }

    @Test
    fun testActualGetMultipleAccounts() = runTest {
        val randomPublicKeys = listOf(
            PublicKey("9VHphpWFmUxVHxzWyeYJYYbQADWZ7X6PLzyWER8Lc3k2"),
            PublicKey("9VHphpWFmUxVHxzWyeYJYYbQADWZ7X6PLzyWER8Lc3k2")
        )
        val rpc = RPC(rpcUrl)
        val metadatas = rpc.getMultipleAccounts(
            randomPublicKeys,
            null,
            serializer = BorshAsBase64JsonArraySerializer(Metadata.serializer())
        )!!
        assertEquals(metadatas.size, 2)
        val first = metadatas[0]!!.data
        assertNotNull(first)
        assertEquals(first.key, Key.MetadataV1)
        assertEquals(first.data.name, "Gooberg #2235")

        val second = metadatas[1]!!.data
        assertNotNull(second)
        assertEquals(second.key, Key.MetadataV1)
        assertEquals(second.data.name, "Gooberg #2235")
    }

    @Test
    fun testActualGetLatestBlockhash() = runTest {
        val rpc = RPC(rpcUrl)
        val blockhash = rpc.getLatestBlockhash(null)
        assertNotNull(blockhash)
        assertNotEquals(blockhash.blockhash, "")
        assertTrue { blockhash.lastValidBlockHeight > 0u }
    }

    @Test
    fun testActualGetSlot() = runTest {
        val rpc = RPC(rpcUrl)
        val slot = rpc.getSlot(null)
        assertNotNull(slot)
        assertTrue { slot > 0u }
    }

    @Test
    fun testActualGetMinimumBalanceForRentExemption() = runTest {
        val rpc = RPC(rpcUrl)
        val minimumBalanceForRentExemption = rpc.getMinimumBalanceForRentExemption(50u)
        assertNotNull(minimumBalanceForRentExemption)
        assertEquals(minimumBalanceForRentExemption, 1238880u)
    }

    @Test
    fun testActualGetBalance() = runTest {
        val rpc = RPC(rpcUrl)
        val publicKey = PublicKey("83astBRguLMdt2h5U1Tpdq5tjFoJ6noeGwaY3mDLVcri")
        val balance = rpc.getBalance(publicKey, null)
        assertNotNull(balance)
        assertNotNull(publicKey)
    }

    /* This is heavily rate limited.
    @Test
    fun testRequestAirdrop() = runTest {
        val rpc = RPC("https://api.devnet.solana.com")
        val signature = rpc.requestAirdrop(RpcRequestAirdropConfiguration(PublicKey("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"), sol(0.1)))
        assertNotNull(signature)
    } */
}
