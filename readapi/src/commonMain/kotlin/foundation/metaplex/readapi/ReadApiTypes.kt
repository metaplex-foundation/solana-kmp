package foundation.metaplex.readapi

import foundation.metaplex.rpc.serializers.PublicKeyAsStringSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class ReadApiAssetInterface(val value: String)

@Serializable
sealed class ReadApiPropGroupKey(val value: String) {
    object Collection : ReadApiPropGroupKey("collection")
}

@Serializable
sealed class ReadApiPropSortBy(val value: String) {
    object Created : ReadApiPropSortBy("created")
    object Updated : ReadApiPropSortBy("updated")
    object RecentAction : ReadApiPropSortBy("recent_action")
}

@Serializable
sealed class ReadApiPropSortDirection(val value: String) {
    object Asc : ReadApiPropSortDirection("asc")
    object Desc : ReadApiPropSortDirection("desc")
}

@Serializable
data class ReadApiParamAssetSortBy(
    val sortBy: ReadApiPropSortBy,
    val sortDirection: ReadApiPropSortDirection
)

@Serializable
data class ReadApiAssetContent(
    val jsonUri: String? = null,
    val metadata: JsonMetadata? = null,
    val files: List<File>? = null,
    val links: Link? = null
)

@Serializable
data class ReadApiAssetCompression(
    val eligible: Boolean,
    val compressed: Boolean,
    @Serializable(with = PublicKeyAsStringSerializer::class) val dataHash: PublicKey? = null,
    @Serializable(with = PublicKeyAsStringSerializer::class) val creatorHash: PublicKey? = null,
    @Serializable(with = PublicKeyAsStringSerializer::class) val assetHash: PublicKey? = null,
    @Serializable(with = PublicKeyAsStringSerializer::class) val tree: PublicKey? = null,
    val seq: Int,
    val leafId: Int? = null
)

@Serializable
data class ReadApiAssetOwnership(
    val frozen: Boolean,
    val delegated: Boolean,
    @Serializable(with = PublicKeyAsStringSerializer::class) val delegate: PublicKey?,
    @Serializable(with = PublicKeyAsStringSerializer::class) val owner: PublicKey,
    val ownershipModel: String? = null
)

@Serializable
data class ReadApiAssetSupply(
    val editionNonce: Int? = null,
    val printCurrentSupply: Int? = null,
    val printMaxSupply: Int? = null
)

@Serializable
data class ReadApiAssetRoyalty(
    val royaltyModel: String? = null,
    @Serializable(with = PublicKeyAsStringSerializer::class) val target: PublicKey?,
    val percent: Double,
    val primarySaleHappened: Boolean? = null,
    val basisPoints: Double? = null,
    val locked: Boolean
)

@Serializable
data class ReadApiAssetCreator(
    @Serializable(with = PublicKeyAsStringSerializer::class) val address: PublicKey,
    val verified: Boolean,
    val share: Int
)
@Serializable
data class ReadApiAssetGrouping(
    val groupKey: ReadApiPropGroupKey? = null,
    val groupValue: String? = null
)


@Serializable
data class ReadApiAssetAuthority(
    @Serializable(with = PublicKeyAsStringSerializer::class) val address: PublicKey,
    val scopes: List<String>
)

@Serializable
data class GetAsset(
    @Serializable(with = PublicKeyAsStringSerializer::class) val id: PublicKey,
)

@Serializable
data class GetAssetProofRpcResponse(
    @Serializable(with = PublicKeyAsStringSerializer::class) val root: PublicKey,
    val proof: List<@Serializable(with = PublicKeyAsStringSerializer::class)PublicKey>,
    val nodeIndex: Int? = null,
    @Serializable(with = PublicKeyAsStringSerializer::class) val leaf: PublicKey,
    @Serializable(with = PublicKeyAsStringSerializer::class) val treeId: PublicKey? = null
)

@Serializable
data class GetAssetsByGroupRpcInput(
    val groupKey: String,
    val groupValue: String,
    val page: Int? = null,
    val limit: Int? = null,
    val before: String? = null,
    val after: String? = null,
    val sortBy: ReadApiParamAssetSortBy? = null
)

@Serializable
data class GetAssetsByOwnerRpcInput(
    @Serializable(with = PublicKeyAsStringSerializer::class) val ownerAddress: PublicKey,
    val page: Int = 1,
    val limit: Int? = null,
    val before: String? = null,
    val after: String? = null,
    val sortBy: ReadApiParamAssetSortBy? = null
)

@Serializable
data class ReadApiAsset(
    @Serializable(with = PublicKeyAsStringSerializer::class) val id: PublicKey,
    val interfaceType: ReadApiAssetInterface? = null,
    val ownership: ReadApiAssetOwnership,
    val mutable: Boolean,
    val authorities: List<ReadApiAssetAuthority>,
    val content: ReadApiAssetContent,
    val royalty: ReadApiAssetRoyalty,
    // val supply: ReadApiAssetSupply,
    val creators: List<ReadApiAssetCreator>,
    val grouping: List<ReadApiAssetGrouping>,
    @Serializable(with = ReadApiAssetCompressionEmptyStringWorkaround::class) val compression: ReadApiAssetCompression
)

object ReadApiAssetCompressionEmptyStringWorkaround
    : JsonTransformingSerializer<ReadApiAssetCompression>(ReadApiAssetCompression.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element.toString().contains("\"\""))
            JsonObject(element.jsonObject.filterValues { it.toString() != "\"\"" }) else element
    }
}

@Serializable
data class ReadApiAssetList(
    val total: Int,
    val limit: Int,
    val items: List<ReadApiAsset>,
    val page: Int? = null,
    val before: String? = null,
    val after: String? = null,
    val errors: List<ReadApiRpcResponseError>? = null
)

@Serializable
data class ReadApiRpcResponseError(
    val error: String,
    val id: String
)