package foundation.metaplex.readapi

import foundation.metaplex.rpc.networking.NetworkDriver
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class ReadApiDecoratorIntegTests {
    private val testingTimeout = 30.seconds
    private var rpcUrl: String = "https://api.mainnet-beta.solana.com"

    private val readApiDecorator = ReadApiDecorator(
        rpcUrl,
        NetworkDriver()
    )

    @Test
    fun testReadApiGetAssetsByOwner() = runTest(timeout = testingTimeout) {
        val randomPublicKey = PublicKey("2RtGg6fsFiiF1EQzHqbd66AhW7R5bWeQGpTbv2UMkCdW")
        val assets = readApiDecorator.getAssetsByOwner(GetAssetsByOwnerRpcInput(randomPublicKey))
        assertTrue { assets.total > 0 }
        val asset = assets.items.find { it.content.metadata?.name == "Mad Lads"}
        assertNotNull(asset)
        assertTrue { asset.content.files!!.first().uri!!.endsWith("collection.png") }
        assertTrue { asset.content.links!!.image!!.endsWith("collection.png") }
    }

    @Test
    fun testReadApiGetAsset() = runTest(timeout = testingTimeout) {
        val randomAssetKey = PublicKey("BWvhiDKg1c1tB2nCSjmT6mxzxDr8RvTzzy8PSsYpFHY3")
        val asset = readApiDecorator.getAsset(randomAssetKey)
        assertEquals(randomAssetKey, asset.id)
        assertEquals("Bread Head | #779", asset.content.metadata!!.name)
        assertEquals("https://www.arweave.net/nMkIdhuoa49pRmATpfo8rgkIBxTrPbCF6JuZqufMJy0?ext=png", asset.content.files?.first()!!.uri)
    }

    @Test
    fun testReadApiGetAssetProof() = runTest(timeout = testingTimeout) {
        val randomAssetKey = PublicKey("5Vaji1rsmhRCXJXPzZgXbfwbVvUtZGR2F9FXaaKQ1cME")
        val assetProof = readApiDecorator.getAssetProof(randomAssetKey)
        assertEquals(PublicKey("GZxgRaFoyxR2Vo3nUKVXQ2716Q7rdisjbNeu1m6SCoyH"), assetProof.proof.first())
    }

    @Test
    fun testReadApiGetAssetsByGroup() = runTest(timeout = testingTimeout) {
        val assets = readApiDecorator.getAssetsByGroup(
            GetAssetsByGroupRpcInput(
                "collection",
                "J2ZfLdQsaZ3GCmbucJef3cPnPwGcgjDW1SSYtMdq3L9p",
                1,
                1000
            )
        )
        assertEquals(1000, assets.total)
        assertEquals(
            PublicKey("JEAAuQNfGk1NsnCLAQo8GYDFJibJVn8NVxwbujzcUk1K"),
            assets.items.first().id
        )
    }
}
