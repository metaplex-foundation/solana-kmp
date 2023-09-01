package foundation.metaplex.solanainterfaces

import com.funkatronics.kborsh.Borsh
import foundation.metaplex.solanainterfaces.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal val base64Data = "BCqZ4aAti/f7wSoPzFf/CrQTG4v0/11BcAetBEg7FbVensCP14TwoRIbVTiKBSRrzepO8TbHY//R3QMT7rCRAK4gAAAAR29vYmVyZyAjMjIzNQAAAAAAAAAAAAAAAAAAAAAAAAAKAAAAVEdCAAAAAAAAAMgAAABodHRwczovL25mdHN0b3JhZ2UubGluay9pcGZzL2JhZnliZWlkM3Z4ZmZtNzd6amMyZXpsenhsa2VqdXNqanBzYWlpeW0ycWJoamIydGdldHFzcHdyN2txLzIyMzUuanNvbgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4CAQIAAAAUxFfsU6QG71ZDthn7yLOtvea+g+ONHxtjbBTouRpqtAEAKpnhoC2L9/vBKg/MV/8KtBMbi/T/XUFwB60ESDsVtV4AZAEBAf8BBAEBDQmAIUVtXc9CP2WLJVKrk306RuMGChFEq/3kKHZmh6oAAAEAAQmGIoXjcQqQ1R2eRwLemp3V6f3IrIHS0qzR4d3IJP7EAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=="
internal val data = """
    ["$base64Data","base64"]
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
}