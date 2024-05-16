package foundation.metaplex.rpc.serializers

import com.funkatronics.kborsh.Borsh
import com.solana.rpccore.Rpc20Response
import foundation.metaplex.rpc.Account
import foundation.metaplex.rpc.AccountInfoSerializer
import foundation.metaplex.rpc.RpcGetAccountInfoConfiguration
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal val base64Data = "BCqZ4aAti/f7wSoPzFf/CrQTG4v0/11BcAetBEg7FbVensCP14TwoRIbVTiKBSRrzepO8TbHY//R3QMT7rCRAK4gAAAAR29vYmVyZyAjMjIzNQAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAVEdCAAAAAAAAAMgAAABodHRwczovL25mdHN0b3JhZ2UubGluay9pcGZzL2JhZnliZWlkM3Z4ZmZtNzd6amMyZXpsenhsa2VqdXNqanBzYWlpeW0ycWJoamIydGdldHFzcHdyN2txLzIyMzUuanNvbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4CAQIAAAAUxFfsU6QG71ZDthn7yLOtvea+g+ONHxtjbBTouRpqtAEAKpnhoC2L9/vBKg/MV/8KtBMbi/T/XUFwB60ESDsVtV4AZAEBAf8BBAEBDQmAIUVtXc9CP2WLJVKrk306RuMGChFEq/3kKHZmh6oAAAEAAQmGIoXjcQqQ1R2eRwLemp3V6f3IrIHS0qzR4d3IJP7EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=="
internal val data = """
    ["$base64Data","base64"]
""".trimIndent()
internal val rpcResponse = """
    {"jsonrpc":"2.0","result":{"context":{"apiVersion":"1.14.26","slot":216147841},"value":{"data":["BCqZ4aAti/f7wSoPzFf/CrQTG4v0/11BcAetBEg7FbVensCP14TwoRIbVTiKBSRrzepO8TbHY//R3QMT7rCRAK4gAAAAR29vYmVyZyAjMjIzNQAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAVEdCAAAAAAAAAMgAAABodHRwczovL25mdHN0b3JhZ2UubGluay9pcGZzL2JhZnliZWlkM3Z4ZmZtNzd6amMyZXpsenhsa2VqdXNqanBzYWlpeW0ycWJoamIydGdldHFzcHdyN2txLzIyMzUuanNvbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4CAQIAAAAUxFfsU6QG71ZDthn7yLOtvea+g+ONHxtjbBTouRpqtAEAKpnhoC2L9/vBKg/MV/8KtBMbi/T/XUFwB60ESDsVtV4AZAEBAf8BBAEBDQmAIUVtXc9CP2WLJVKrk306RuMGChFEq/3kKHZmh6oAAAEAAQmGIoXjcQqQ1R2eRwLemp3V6f3IrIHS0qzR4d3IJP7EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==","base64"],"executable":false,"lamports":5616720,"owner":"metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s","rentEpoch":0}},"id":"3851293147"}
""".trimIndent()

internal val multipleAccounts = """
    {"jsonrpc":"2.0","result":{"context":{"apiVersion":"1.14.26","slot":216387148},"value":[{"data":["BCqZ4aAti/f7wSoPzFf/CrQTG4v0/11BcAetBEg7FbVensCP14TwoRIbVTiKBSRrzepO8TbHY//R3QMT7rCRAK4gAAAAR29vYmVyZyAjMjIzNQAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAVEdCAAAAAAAAAMgAAABodHRwczovL25mdHN0b3JhZ2UubGluay9pcGZzL2JhZnliZWlkM3Z4ZmZtNzd6amMyZXpsenhsa2VqdXNqanBzYWlpeW0ycWJoamIydGdldHFzcHdyN2txLzIyMzUuanNvbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4CAQIAAAAUxFfsU6QG71ZDthn7yLOtvea+g+ONHxtjbBTouRpqtAEAKpnhoC2L9/vBKg/MV/8KtBMbi/T/XUFwB60ESDsVtV4AZAEBAf8BBAEBDQmAIUVtXc9CP2WLJVKrk306RuMGChFEq/3kKHZmh6oAAAEAAQmGIoXjcQqQ1R2eRwLemp3V6f3IrIHS0qzR4d3IJP7EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==","base64"],"executable":false,"lamports":5616720,"owner":"metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s","rentEpoch":0},{"data":["BCqZ4aAti/f7wSoPzFf/CrQTG4v0/11BcAetBEg7FbVensCP14TwoRIbVTiKBSRrzepO8TbHY//R3QMT7rCRAK4gAAAAR29vYmVyZyAjMjIzNQAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAVEdCAAAAAAAAAMgAAABodHRwczovL25mdHN0b3JhZ2UubGluay9pcGZzL2JhZnliZWlkM3Z4ZmZtNzd6amMyZXpsenhsa2VqdXNqanBzYWlpeW0ycWJoamIydGdldHFzcHdyN2txLzIyMzUuanNvbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4CAQIAAAAUxFfsU6QG71ZDthn7yLOtvea+g+ONHxtjbBTouRpqtAEAKpnhoC2L9/vBKg/MV/8KtBMbi/T/XUFwB60ESDsVtV4AZAEBAf8BBAEBDQmAIUVtXc9CP2WLJVKrk306RuMGChFEq/3kKHZmh6oAAAEAAQmGIoXjcQqQ1R2eRwLemp3V6f3IrIHS0qzR4d3IJP7EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==","base64"],"executable":false,"lamports":5616720,"owner":"metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s","rentEpoch":0}]},"id":"2746154718"}
""".trimIndent()

@Serializable
enum class Key {
    Uninitialized,
    EditionV1,
    MasterEditionV1,
    ReservationListV1,
    MetadataV1,
    ReservationListV2,
    MasterEditionV2,
    EditionMarker,
    UseAuthorityRecord,
    CollectionAuthorityRecord,
    TokenOwnedEscrow,
    TokenRecord,
    MetadataDelegate,
    EditionMarkerV2
}

@Serializable
data class Creator(
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val address: PublicKey,
    val verified: Boolean,
    val share: UByte
)

@Serializable
data class Data(
    val name: String,
    val symbol: String,
    val uri: String,
    val sellerFeeBasisPoints: UShort,
    val creators: List<Creator>?
)
@Serializable
class Metadata(
    val key: Key,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val updateAuthority: PublicKey,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val mint: PublicKey,
    val data: Data,
    val primarySaleHappened: Boolean,
    val isMutable: Boolean,
)

class SerializerTests {

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testBorshSerializer() = runTest {
        val decodedData: ByteArray = Base64.decode(base64Data)
        val metadata: Metadata = Borsh.decodeFromByteArray(Metadata.serializer(), decodedData)
        assertNotNull(metadata)
        assertEquals(metadata.key, Key.MetadataV1)
        assertEquals(metadata.data.name, "Gooberg #2235")
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testDataSerializer() = runTest {
        val serializer = BorshAsBase64JsonArraySerializer(Metadata.serializer())
        val metadata = Json.decodeFromString(serializer, data)
        assertNotNull(metadata)
        assertEquals(metadata.key, Key.MetadataV1)
        assertEquals(metadata.data.name, "Gooberg #2235")
    }

    @Test
    fun testResponseSerializer() = runTest {
        val json = Json {
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }
        val serializer = Rpc20Response.serializer(
            AccountInfoSerializer(BorshAsBase64JsonArraySerializer(Metadata.serializer()))
        )
        val metadata = json.decodeFromString(serializer, rpcResponse).result!!.data
        assertNotNull(metadata)
        assertEquals(metadata.key, Key.MetadataV1)
        assertEquals(metadata.data.name, "Gooberg #2235")
    }

    @Test
    fun testEncodeParamsList() = runTest {
        val json = Json {
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = false
        }

        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(
            PublicKey("9VHphpWFmUxVHxzWyeYJYYbQADWZ7X6PLzyWER8Lc3k2").toBase58()
        ))
        params.add(json.encodeToJsonElement(
            RpcGetAccountInfoConfiguration.serializer(),
            RpcGetAccountInfoConfiguration()
        ))

        assertNotNull(params)
        assertTrue { params.count() == 2 }
        val encodedString = json.encodeToString(params)
        assertEquals(encodedString, """
            [
                "9VHphpWFmUxVHxzWyeYJYYbQADWZ7X6PLzyWER8Lc3k2",
                {
                    "encoding": "base64"
                }
            ]
        """.trimIndent())
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testGetMultipleAccountsSerializer() = runTest {
        val json = Json {
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }
        val serializer = Rpc20Response.serializer(
            SolanaResponseSerializer(
                ListSerializer(
                    Account.serializer(
                        BorshAsBase64JsonArraySerializer(Metadata.serializer())
                    )
                )
            )
        )
        val metadatas = json.decodeFromString(serializer, multipleAccounts).result!!
        assertEquals(metadatas.size, 2)
    }
}