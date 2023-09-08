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

class RpcTests {
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
    fun testActualGetLatestBlockhash() = runTest {
        val rpc = RPC(rpcUrl)
        val blockhash = rpc.getLatestBlockhash(null)
        assertNotNull(blockhash)
        assertNotEquals(blockhash.blockhash, "")
        assertTrue { blockhash.lastValidBlockHeight > 0u }
    }
}