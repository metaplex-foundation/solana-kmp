package foundation.metaplex.readapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class JsonMetadata(
    val name: String? = null,
    val symbol: String? = null,
    val description: String? = null,
    val sellerFeeBasisPoints: Int? = null,
    val image: String? = null,
    val animationUrl: String? = null,
    val externalUrl: String? = null,
    val attributes: List<Attribute>? = null,
    val properties: Properties? = null ,
    val collection: Collection? = null,
    val unknownFields: JsonObject? = null
)

@Serializable
data class Attribute(
    val traitType: String? = null,
    val value: JsonElement? = null,
    val unknownFields: JsonObject? = null
)

@Serializable
data class Properties(
    val creators: List<Creator>? = null,
    val files: List<File>? = null,
    val unknownFields: JsonObject? = null
)

@Serializable
data class Creator(
    val address: String? = null,
    val share: Int? = null,
    val unknownFields: JsonObject? = null
)

@Serializable
data class File(
    val type: String? = null,
    val uri: String? = null,
    val mime: String? = null,
    val unknownFields: JsonObject? = null
)

@Serializable
data class Link(
    val image: String? = null,
    val external_url: String? = null,
    val animation_url: String? = null,
)


@Serializable
data class Collection(
    val name: String? = null,
    val family: String? = null,
    val unknownFields: JsonObject? = null
)
