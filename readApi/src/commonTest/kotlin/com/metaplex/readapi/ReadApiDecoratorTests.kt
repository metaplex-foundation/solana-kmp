package com.metaplex.readapi

import com.metaplex.ktorDriver.kTorDriver
import com.metaplex.solana_public_keys.PublicKey
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReadApiDecoratorTests {

    private val readApiDecorator = ReadApiDecorator(
        "https://rpc.helius.xyz/?api-key=2a834b7b-8698-41db-9d08-fc0c68e45cc7",
        kTorDriver(HttpClient(CIO))
    )

    @Test
    fun testReadApiGetAssetsByOwner() = runTest {
        val randomPublicKey = PublicKey("Geh5Ss5knQGym81toYGXDbH3MFU2JCMK7E4QyeBHor1b")
        val readApiDecorator = ReadApiDecorator("https://rpc.helius.xyz/?api-key=2a834b7b-8698-41db-9d08-fc0c68e45cc7", kTorDriver(HttpClient(
            CIO
        )))
        val assets = readApiDecorator.getAssetsByOwner(GetAssetsByOwnerRpcInput(randomPublicKey))
        assertEquals(assets.total, 214)
    }

    @Test
    fun testReadApiGetAsset() = runTest {
        val randomAssetKey = PublicKey("BWvhiDKg1c1tB2nCSjmT6mxzxDr8RvTzzy8PSsYpFHY3")
        val asset = readApiDecorator.getAsset(randomAssetKey)
        assertEquals(asset.id, randomAssetKey)
        assertEquals(asset.content.metadata?.name, "Bread Head | #779")
    }

    @Test
    fun testReadApiGetAssetProof() = runTest {
        val randomAssetKey = PublicKey("5Vaji1rsmhRCXJXPzZgXbfwbVvUtZGR2F9FXaaKQ1cME")
        val assetProof = readApiDecorator.getAssetProof(randomAssetKey)
        assertEquals(assetProof.proof.first(), PublicKey("GZxgRaFoyxR2Vo3nUKVXQ2716Q7rdisjbNeu1m6SCoyH"))
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
            PublicKey("CMVuYDS9nTeujfTPJb8ik7CRhAqZv4DfjfdamFLkJgxE")
        )
    }
}