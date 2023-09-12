package foundation.metaplex.readapi

import foundation.metaplex.rpc.networking.NetworkDriver
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReadApiDecoratorTests {
    private var rpcUrl: String = "https://api.mainnet-beta.solana.com"

    private val readApiDecorator = ReadApiDecorator(
        rpcUrl,
        NetworkDriver()
    )

    @Test
    fun testReadApiGetAssetsByOwner() = runTest {
        val randomPublicKey = PublicKey("Geh5Ss5knQGym81toYGXDbH3MFU2JCMK7E4QyeBHor1b")
        val assets = readApiDecorator.getAssetsByOwner(GetAssetsByOwnerRpcInput(randomPublicKey))
        assertTrue { assets.total > 0 }
        assertEquals("RedButo #1911", assets.items[1].content.metadata!!.name, )
        assertEquals("https://www.arweave.net/Na-z1R0HXNLh9NkuP9cxq7p9fb8KaJt6QX2Bds5hRig?ext=png", assets.items[1].content.files!!.first().uri)
        assertEquals("https://www.arweave.net/Na-z1R0HXNLh9NkuP9cxq7p9fb8KaJt6QX2Bds5hRig?ext=png", assets.items[1].content.links!!.image)
    }

    @Test
    fun testReadApiGetAsset() = runTest {
        val randomAssetKey = PublicKey("BWvhiDKg1c1tB2nCSjmT6mxzxDr8RvTzzy8PSsYpFHY3")
        val asset = readApiDecorator.getAsset(randomAssetKey)
        assertEquals(randomAssetKey, asset.id)
        assertEquals("Bread Head | #779", asset.content.metadata!!.name)
        assertEquals("https://www.arweave.net/nMkIdhuoa49pRmATpfo8rgkIBxTrPbCF6JuZqufMJy0?ext=png", asset.content.files?.first()!!.uri)
    }

    @Test
    fun testReadApiGetAssetProof() = runTest {
        val randomAssetKey = PublicKey("5Vaji1rsmhRCXJXPzZgXbfwbVvUtZGR2F9FXaaKQ1cME")
        val assetProof = readApiDecorator.getAssetProof(randomAssetKey)
        assertEquals(PublicKey("GZxgRaFoyxR2Vo3nUKVXQ2716Q7rdisjbNeu1m6SCoyH"), assetProof.proof.first())
    }

    @Test
    fun testReadApiGetAssetsByGroup() = runTest {
        val assets = readApiDecorator.getAssetsByGroup(
            GetAssetsByGroupRpcInput(
                "collection",
                "J1S9H3QjnRtBbbuD4HjPV6RpRhwuk4zKbxsnCHuTgh9w",
                1,
                1000
            )
        )
        assertEquals(assets.total, 1000)
        assertEquals(
            assets.items.first().id,
            PublicKey("GVPX9rXRXo9SVGktJCzA3Qb9v263kQzEyAWsgX3LL8P5")
        )
    }
}