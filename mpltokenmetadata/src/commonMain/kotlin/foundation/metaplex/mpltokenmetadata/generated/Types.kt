//
// Types
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-07-18
//
@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mpltokenmetadata.generated

import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.Boolean
import kotlin.ByteArray
import kotlin.String
import kotlin.UByte
import kotlin.ULong
import kotlin.UShort
import kotlin.collections.List
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class SetCollectionSizeArgs(val size: ULong)

@Serializable
data class CreateMasterEditionArgs(val maxSupply: ULong?)

@Serializable
data class MintNewEditionFromMasterEditionViaTokenArgs(val edition: ULong)

@Serializable
data class TransferOutOfEscrowArgs(val amount: ULong)

@Serializable
data class CreateMetadataAccountArgsV3(
    val data: DataV2,
    val isMutable: Boolean,
    val collectionDetails: CollectionDetails?
)

@Serializable
data class UpdateMetadataAccountArgsV2(
    val data: DataV2?,
    val updateAuthority: PublicKey?,
    val primarySaleHappened: Boolean?,
    val isMutable: Boolean?
)

@Serializable
data class ApproveUseAuthorityArgs(val numberOfUses: ULong)

@Serializable
data class UtilizeArgs(val numberOfUses: ULong)

@Serializable
data class AuthorizationData(val payload: Payload)

@Serializable
data class AssetData(
    val name: String,
    val symbol: String,
    val uri: String,
    val sellerFeeBasisPoints: UShort,
    val creators: List<Creator>?,
    val primarySaleHappened: Boolean,
    val isMutable: Boolean,
    val tokenStandard: TokenStandard,
    val collection: Collection?,
    val uses: Uses?,
    val collectionDetails: CollectionDetails?,
    val ruleSet: PublicKey?
)

@Serializable
data class Collection(val verified: Boolean, val key: PublicKey)

@Serializable
data class Creator(
    val address: PublicKey,
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
data class DataV2(
    val name: String,
    val symbol: String,
    val uri: String,
    val sellerFeeBasisPoints: UShort,
    val creators: List<Creator>?,
    val collection: Collection?,
    val uses: Uses?
)

@Serializable
data class Reservation(
    val address: PublicKey,
    val spotsRemaining: ULong,
    val totalSpots: ULong
)

@Serializable
data class ReservationV1(
    val address: PublicKey,
    val spotsRemaining: UByte,
    val totalSpots: UByte
)

@Serializable
data class SeedsVec(val seeds: List<ByteArray>)

@Serializable
data class LeafInfo(val leaf: List<UByte>, val proof: List<List<UByte>>)

@Serializable
data class Payload(val map: HashMap<String, PayloadType>)

@Serializable
data class Uses(
    val useMethod: UseMethod,
    val remaining: ULong,
    val total: ULong
)

@Serializable(with = BurnArgsSerializer::class)
sealed class BurnArgs {
    data class V1(val amount: ULong) : BurnArgs()
}

class BurnArgsSerializer : KSerializer<BurnArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: BurnArgs) {
        when(value){ 
           is BurnArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): BurnArgs = when(decoder.decodeByte().toInt()){
       0 -> BurnArgs.V1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = DelegateArgsSerializer::class)
sealed class DelegateArgs {
    data class CollectionV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class SaleV1(val amount: ULong, val authorization_data: AuthorizationData?) :
            DelegateArgs()

    data class TransferV1(val amount: ULong, val authorization_data: AuthorizationData?) :
            DelegateArgs()

    data class DataV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class UtilityV1(val amount: ULong, val authorization_data: AuthorizationData?) :
            DelegateArgs()

    data class StakingV1(val amount: ULong, val authorization_data: AuthorizationData?) :
            DelegateArgs()

    data class StandardV1(val amount: ULong) : DelegateArgs()

    data class LockedTransferV1(
        val amount: ULong,
        val locked_address: PublicKey,
        val authorization_data: AuthorizationData?
    ) : DelegateArgs()

    data class ProgrammableConfigV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class AuthorityItemV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class DataItemV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class CollectionItemV1(val authorization_data: AuthorizationData?) : DelegateArgs()

    data class ProgrammableConfigItemV1(val authorization_data: AuthorizationData?) : DelegateArgs()
}

class DelegateArgsSerializer : KSerializer<DelegateArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: DelegateArgs) {
        when(value){ 
           is DelegateArgs.CollectionV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.SaleV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.TransferV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.DataV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 3.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.UtilityV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 4.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.StakingV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 5.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.StandardV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 6.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
           }
           is DelegateArgs.LockedTransferV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 7.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.locked_address)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.ProgrammableConfigV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 8.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.AuthorityItemV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 9.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.DataItemV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 10.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.CollectionItemV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 11.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is DelegateArgs.ProgrammableConfigItemV1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 12.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): DelegateArgs = when(decoder.decodeByte().toInt()){
       0 -> DelegateArgs.CollectionV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   1 -> DelegateArgs.SaleV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   2 -> DelegateArgs.TransferV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   3 -> DelegateArgs.DataV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   4 -> DelegateArgs.UtilityV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   5 -> DelegateArgs.StakingV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   6 -> DelegateArgs.StandardV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   7 -> DelegateArgs.LockedTransferV1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           locked_address = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   8 -> DelegateArgs.ProgrammableConfigV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   9 -> DelegateArgs.AuthorityItemV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   10 -> DelegateArgs.DataItemV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   11 -> DelegateArgs.CollectionItemV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   12 -> DelegateArgs.ProgrammableConfigItemV1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class RevokeArgs {
    CollectionV1,

    SaleV1,

    TransferV1,

    DataV1,

    UtilityV1,

    StakingV1,

    StandardV1,

    LockedTransferV1,

    ProgrammableConfigV1,

    MigrationV1,

    AuthorityItemV1,

    DataItemV1,

    CollectionItemV1,

    ProgrammableConfigItemV1
}

@Serializable
enum class MetadataDelegateRole {
    AuthorityItem,

    Collection,

    Use,

    Data,

    ProgrammableConfig,

    DataItem,

    CollectionItem,

    ProgrammableConfigItem
}

@Serializable(with = CreateArgsSerializer::class)
sealed class CreateArgs {
    data class V1(
        val asset_data: AssetData,
        val decimals: UByte?,
        val print_supply: PrintSupply?
    ) : CreateArgs()
}

class CreateArgsSerializer : KSerializer<CreateArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: CreateArgs) {
        when(value){ 
           is CreateArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

              
                encoder.encodeSerializableValue(AssetData.serializer(),
                value.asset_data)
               encoder.encodeSerializableValue(kotlin.UByte.serializer().nullable, value.decimals)
              
                encoder.encodeSerializableValue(PrintSupply.serializer().nullable,
                value.print_supply)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): CreateArgs = when(decoder.decodeByte().toInt()){
       0 -> CreateArgs.V1 (
           asset_data =
            decoder.decodeSerializableValue(AssetData.serializer()),
           decimals = decoder.decodeSerializableValue(kotlin.UByte.serializer().nullable),
           print_supply =
            decoder.decodeSerializableValue(PrintSupply.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = MintArgsSerializer::class)
sealed class MintArgs {
    data class V1(val amount: ULong, val authorization_data: AuthorizationData?) : MintArgs()
}

class MintArgsSerializer : KSerializer<MintArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: MintArgs) {
        when(value){ 
           is MintArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): MintArgs = when(decoder.decodeByte().toInt()){
       0 -> MintArgs.V1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = TransferArgsSerializer::class)
sealed class TransferArgs {
    data class V1(val amount: ULong, val authorization_data: AuthorizationData?) : TransferArgs()
}

class TransferArgsSerializer : KSerializer<TransferArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: TransferArgs) {
        when(value){ 
           is TransferArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.amount)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): TransferArgs = when(decoder.decodeByte().toInt()){
       0 -> TransferArgs.V1 (
           amount = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = UpdateArgsSerializer::class)
sealed class UpdateArgs {
    data class V1(
        val new_update_authority: PublicKey?,
        val data: Data?,
        val primary_sale_happened: Boolean?,
        val is_mutable: Boolean?,
        val collection: CollectionToggle,
        val collection_details: CollectionDetailsToggle,
        val uses: UsesToggle,
        val rule_set: RuleSetToggle,
        val authorization_data: AuthorizationData?
    ) : UpdateArgs()

    data class AsUpdateAuthorityV2(
        val new_update_authority: PublicKey?,
        val data: Data?,
        val primary_sale_happened: Boolean?,
        val is_mutable: Boolean?,
        val collection: CollectionToggle,
        val collection_details: CollectionDetailsToggle,
        val uses: UsesToggle,
        val rule_set: RuleSetToggle,
        val token_standard: TokenStandard?,
        val authorization_data: AuthorizationData?
    ) : UpdateArgs()

    data class AsAuthorityItemDelegateV2(
        val new_update_authority: PublicKey?,
        val primary_sale_happened: Boolean?,
        val is_mutable: Boolean?,
        val token_standard: TokenStandard?,
        val authorization_data: AuthorizationData?
    ) : UpdateArgs()

    data class AsCollectionDelegateV2(val collection: CollectionToggle, val authorization_data:
            AuthorizationData?) : UpdateArgs()

    data class AsDataDelegateV2(val data: Data?, val authorization_data: AuthorizationData?) :
            UpdateArgs()

    data class AsProgrammableConfigDelegateV2(val rule_set: RuleSetToggle, val authorization_data:
            AuthorizationData?) : UpdateArgs()

    data class AsDataItemDelegateV2(val data: Data?, val authorization_data: AuthorizationData?) :
            UpdateArgs()

    data class AsCollectionItemDelegateV2(val collection: CollectionToggle, val authorization_data:
            AuthorizationData?) : UpdateArgs()

    data class AsProgrammableConfigItemDelegateV2(val rule_set: RuleSetToggle, val
            authorization_data: AuthorizationData?) : UpdateArgs()
}

class UpdateArgsSerializer : KSerializer<UpdateArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: UpdateArgs) {
        when(value){ 
           is UpdateArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer.nullable,
                value.new_update_authority)
              
                encoder.encodeSerializableValue(Data.serializer().nullable,
                value.data)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.primary_sale_happened)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.is_mutable)
              
                encoder.encodeSerializableValue(CollectionToggle.serializer(),
                value.collection)
              
                encoder.encodeSerializableValue(CollectionDetailsToggle.serializer(),
                value.collection_details)
              
                encoder.encodeSerializableValue(UsesToggle.serializer(),
                value.uses)
              
                encoder.encodeSerializableValue(RuleSetToggle.serializer(),
                value.rule_set)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsUpdateAuthorityV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer.nullable,
                value.new_update_authority)
              
                encoder.encodeSerializableValue(Data.serializer().nullable,
                value.data)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.primary_sale_happened)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.is_mutable)
              
                encoder.encodeSerializableValue(CollectionToggle.serializer(),
                value.collection)
              
                encoder.encodeSerializableValue(CollectionDetailsToggle.serializer(),
                value.collection_details)
              
                encoder.encodeSerializableValue(UsesToggle.serializer(),
                value.uses)
              
                encoder.encodeSerializableValue(RuleSetToggle.serializer(),
                value.rule_set)
              
                encoder.encodeSerializableValue(TokenStandard.serializer().nullable,
                value.token_standard)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsAuthorityItemDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer.nullable,
                value.new_update_authority)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.primary_sale_happened)
               encoder.encodeSerializableValue(kotlin.Boolean.serializer().nullable,
                value.is_mutable)
              
                encoder.encodeSerializableValue(TokenStandard.serializer().nullable,
                value.token_standard)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsCollectionDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 3.toByte()) 

              
                encoder.encodeSerializableValue(CollectionToggle.serializer(),
                value.collection)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsDataDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 4.toByte()) 

              
                encoder.encodeSerializableValue(Data.serializer().nullable,
                value.data)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsProgrammableConfigDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 5.toByte()) 

              
                encoder.encodeSerializableValue(RuleSetToggle.serializer(),
                value.rule_set)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsDataItemDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 6.toByte()) 

              
                encoder.encodeSerializableValue(Data.serializer().nullable,
                value.data)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsCollectionItemDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 7.toByte()) 

              
                encoder.encodeSerializableValue(CollectionToggle.serializer(),
                value.collection)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           is UpdateArgs.AsProgrammableConfigItemDelegateV2 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 8.toByte()) 

              
                encoder.encodeSerializableValue(RuleSetToggle.serializer(),
                value.rule_set)
              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): UpdateArgs = when(decoder.decodeByte().toInt()){
       0 -> UpdateArgs.V1 (
           new_update_authority =
            decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer.nullable),
           data =
            decoder.decodeSerializableValue(Data.serializer().nullable),
           primary_sale_happened =
            decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           is_mutable = decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           collection =
            decoder.decodeSerializableValue(CollectionToggle.serializer()),
           collection_details =
            decoder.decodeSerializableValue(CollectionDetailsToggle.serializer()),
           uses =
            decoder.decodeSerializableValue(UsesToggle.serializer()),
           rule_set =
            decoder.decodeSerializableValue(RuleSetToggle.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   1 -> UpdateArgs.AsUpdateAuthorityV2 (
           new_update_authority =
            decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer.nullable),
           data =
            decoder.decodeSerializableValue(Data.serializer().nullable),
           primary_sale_happened =
            decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           is_mutable = decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           collection =
            decoder.decodeSerializableValue(CollectionToggle.serializer()),
           collection_details =
            decoder.decodeSerializableValue(CollectionDetailsToggle.serializer()),
           uses =
            decoder.decodeSerializableValue(UsesToggle.serializer()),
           rule_set =
            decoder.decodeSerializableValue(RuleSetToggle.serializer()),
           token_standard =
            decoder.decodeSerializableValue(TokenStandard.serializer().nullable),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   2 -> UpdateArgs.AsAuthorityItemDelegateV2 (
           new_update_authority =
            decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer.nullable),
           primary_sale_happened =
            decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           is_mutable = decoder.decodeSerializableValue(kotlin.Boolean.serializer().nullable),
           token_standard =
            decoder.decodeSerializableValue(TokenStandard.serializer().nullable),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   3 -> UpdateArgs.AsCollectionDelegateV2 (
           collection =
            decoder.decodeSerializableValue(CollectionToggle.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   4 -> UpdateArgs.AsDataDelegateV2 (
           data =
            decoder.decodeSerializableValue(Data.serializer().nullable),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   5 -> UpdateArgs.AsProgrammableConfigDelegateV2 (
           rule_set =
            decoder.decodeSerializableValue(RuleSetToggle.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   6 -> UpdateArgs.AsDataItemDelegateV2 (
           data =
            decoder.decodeSerializableValue(Data.serializer().nullable),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   7 -> UpdateArgs.AsCollectionItemDelegateV2 (
           collection =
            decoder.decodeSerializableValue(CollectionToggle.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   8 -> UpdateArgs.AsProgrammableConfigItemDelegateV2 (
           rule_set =
            decoder.decodeSerializableValue(RuleSetToggle.serializer()),
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = CollectionToggleSerializer::class)
sealed class CollectionToggle {
    object None : CollectionToggle()

    object Clear : CollectionToggle()

    data class Set(val collection: Collection) : CollectionToggle()
}

class CollectionToggleSerializer : KSerializer<CollectionToggle> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: CollectionToggle) {
        when(value){ 
           is CollectionToggle.None -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is CollectionToggle.Clear -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

           }
           is CollectionToggle.Set -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(Collection.serializer(), value.collection)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): CollectionToggle =
            when(decoder.decodeByte().toInt()){
       0 -> CollectionToggle.None 
       1 -> CollectionToggle.Clear 
       2 -> CollectionToggle.Set (
           collection = decoder.decodeSerializableValue(Collection.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = UsesToggleSerializer::class)
sealed class UsesToggle {
    object None : UsesToggle()

    object Clear : UsesToggle()

    data class Set(val uses: Uses) : UsesToggle()
}

class UsesToggleSerializer : KSerializer<UsesToggle> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: UsesToggle) {
        when(value){ 
           is UsesToggle.None -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is UsesToggle.Clear -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

           }
           is UsesToggle.Set -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(Uses.serializer(), value.uses)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): UsesToggle = when(decoder.decodeByte().toInt()){
       0 -> UsesToggle.None 
       1 -> UsesToggle.Clear 
       2 -> UsesToggle.Set (
           uses = decoder.decodeSerializableValue(Uses.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = CollectionDetailsToggleSerializer::class)
sealed class CollectionDetailsToggle {
    object None : CollectionDetailsToggle()

    object Clear : CollectionDetailsToggle()

    data class Set(val collectiondetails: CollectionDetails) : CollectionDetailsToggle()
}

class CollectionDetailsToggleSerializer : KSerializer<CollectionDetailsToggle> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: CollectionDetailsToggle) {
        when(value){ 
           is CollectionDetailsToggle.None -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is CollectionDetailsToggle.Clear -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

           }
           is CollectionDetailsToggle.Set -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(CollectionDetails.serializer(),
                value.collectiondetails)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): CollectionDetailsToggle =
            when(decoder.decodeByte().toInt()){
       0 -> CollectionDetailsToggle.None 
       1 -> CollectionDetailsToggle.Clear 
       2 -> CollectionDetailsToggle.Set (
           collectiondetails = decoder.decodeSerializableValue(CollectionDetails.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = RuleSetToggleSerializer::class)
sealed class RuleSetToggle {
    object None : RuleSetToggle()

    object Clear : RuleSetToggle()

    data class Set(val publicKey: PublicKey) : RuleSetToggle()
}

class RuleSetToggleSerializer : KSerializer<RuleSetToggle> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: RuleSetToggle) {
        when(value){ 
           is RuleSetToggle.None -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is RuleSetToggle.Clear -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

           }
           is RuleSetToggle.Set -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.publicKey)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): RuleSetToggle = when(decoder.decodeByte().toInt()){
       0 -> RuleSetToggle.None 
       1 -> RuleSetToggle.Clear 
       2 -> RuleSetToggle.Set (
           publicKey = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = PrintArgsSerializer::class)
sealed class PrintArgs {
    data class V1(val edition: ULong) : PrintArgs()
}

class PrintArgsSerializer : KSerializer<PrintArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: PrintArgs) {
        when(value){ 
           is PrintArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.edition)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): PrintArgs = when(decoder.decodeByte().toInt()){
       0 -> PrintArgs.V1 (
           edition = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = LockArgsSerializer::class)
sealed class LockArgs {
    data class V1(val authorization_data: AuthorizationData?) : LockArgs()
}

class LockArgsSerializer : KSerializer<LockArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: LockArgs) {
        when(value){ 
           is LockArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): LockArgs = when(decoder.decodeByte().toInt()){
       0 -> LockArgs.V1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = UnlockArgsSerializer::class)
sealed class UnlockArgs {
    data class V1(val authorization_data: AuthorizationData?) : UnlockArgs()
}

class UnlockArgsSerializer : KSerializer<UnlockArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: UnlockArgs) {
        when(value){ 
           is UnlockArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): UnlockArgs = when(decoder.decodeByte().toInt()){
       0 -> UnlockArgs.V1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = UseArgsSerializer::class)
sealed class UseArgs {
    data class V1(val authorization_data: AuthorizationData?) : UseArgs()
}

class UseArgsSerializer : KSerializer<UseArgs> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: UseArgs) {
        when(value){ 
           is UseArgs.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

              
                encoder.encodeSerializableValue(AuthorizationData.serializer().nullable,
                value.authorization_data)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): UseArgs = when(decoder.decodeByte().toInt()){
       0 -> UseArgs.V1 (
           authorization_data =
            decoder.decodeSerializableValue(AuthorizationData.serializer().nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class VerificationArgs {
    CreatorV1,

    CollectionV1
}

@Serializable
enum class TokenStandard {
    NonFungible,

    FungibleAsset,

    Fungible,

    NonFungibleEdition,

    ProgrammableNonFungible,

    ProgrammableNonFungibleEdition
}

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

@Serializable(with = CollectionDetailsSerializer::class)
sealed class CollectionDetails {
    data class V1(val size: ULong) : CollectionDetails()
}

class CollectionDetailsSerializer : KSerializer<CollectionDetails> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: CollectionDetails) {
        when(value){ 
           is CollectionDetails.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.size)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): CollectionDetails =
            when(decoder.decodeByte().toInt()){
       0 -> CollectionDetails.V1 (
           size = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = EscrowAuthoritySerializer::class)
sealed class EscrowAuthority {
    object TokenOwner : EscrowAuthority()

    data class Creator(val publicKey: PublicKey) : EscrowAuthority()
}

class EscrowAuthoritySerializer : KSerializer<EscrowAuthority> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: EscrowAuthority) {
        when(value){ 
           is EscrowAuthority.TokenOwner -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is EscrowAuthority.Creator -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.publicKey)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): EscrowAuthority =
            when(decoder.decodeByte().toInt()){
       0 -> EscrowAuthority.TokenOwner 
       1 -> EscrowAuthority.Creator (
           publicKey = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = PrintSupplySerializer::class)
sealed class PrintSupply {
    object Zero : PrintSupply()

    data class Limited(val u64: ULong) : PrintSupply()

    object Unlimited : PrintSupply()
}

class PrintSupplySerializer : KSerializer<PrintSupply> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: PrintSupply) {
        when(value){ 
           is PrintSupply.Zero -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

           }
           is PrintSupply.Limited -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.u64)
           }
           is PrintSupply.Unlimited -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): PrintSupply = when(decoder.decodeByte().toInt()){
       0 -> PrintSupply.Zero 
       1 -> PrintSupply.Limited (
           u64 = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   2 -> PrintSupply.Unlimited 
       else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable(with = ProgrammableConfigSerializer::class)
sealed class ProgrammableConfig {
    data class V1(val rule_set: PublicKey?) : ProgrammableConfig()
}

class ProgrammableConfigSerializer : KSerializer<ProgrammableConfig> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ProgrammableConfig) {
        when(value){ 
           is ProgrammableConfig.V1 -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer.nullable, value.rule_set)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): ProgrammableConfig =
            when(decoder.decodeByte().toInt()){
       0 -> ProgrammableConfig.V1 (
           rule_set = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer.nullable),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class MigrationType {
    CollectionV1,

    ProgrammableV1
}

@Serializable
enum class TokenState {
    Unlocked,

    Locked,

    Listed
}

@Serializable
enum class TokenDelegateRole {
    Sale,

    Transfer,

    Utility,

    Staking,

    Standard,

    LockedTransfer,

    Migration
}

@Serializable
enum class AuthorityType {
    None,

    Metadata,

    Holder,

    MetadataDelegate,

    TokenDelegate
}

@Serializable
enum class PayloadKey {
    Amount,

    Authority,

    AuthoritySeeds,

    Delegate,

    DelegateSeeds,

    Destination,

    DestinationSeeds,

    Holder,

    Source,

    SourceSeeds
}

@Serializable(with = PayloadTypeSerializer::class)
sealed class PayloadType {
    data class Pubkey(val publicKey: PublicKey) : PayloadType()

    data class Seeds(val seedsvec: SeedsVec) : PayloadType()

    data class MerkleProof(val leafinfo: LeafInfo) : PayloadType()

    data class Number(val u64: ULong) : PayloadType()
}

class PayloadTypeSerializer : KSerializer<PayloadType> {
    override val descriptor: SerialDescriptor =
            kotlinx.serialization.json.JsonObject.serializer().descriptor

    override fun serialize(encoder: Encoder, value: PayloadType) {
        when(value){ 
           is PayloadType.Pubkey -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 0.toByte()) 

               encoder.encodeSerializableValue(PublicKeyAs32ByteSerializer, value.publicKey)
           }
           is PayloadType.Seeds -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 1.toByte()) 

               encoder.encodeSerializableValue(SeedsVec.serializer(), value.seedsvec)
           }
           is PayloadType.MerkleProof -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 2.toByte()) 

               encoder.encodeSerializableValue(LeafInfo.serializer(), value.leafinfo)
           }
           is PayloadType.Number -> { 
               encoder.encodeSerializableValue(Byte.serializer(), 3.toByte()) 

               encoder.encodeSerializableValue(kotlin.ULong.serializer(), value.u64)
           }
           else -> { throw Throwable("Can not serialize")}
        }
    }

    override fun deserialize(decoder: Decoder): PayloadType = when(decoder.decodeByte().toInt()){
       0 -> PayloadType.Pubkey (
           publicKey = decoder.decodeSerializableValue(PublicKeyAs32ByteSerializer),
     )   1 -> PayloadType.Seeds (
           seedsvec = decoder.decodeSerializableValue(SeedsVec.serializer()),
     )   2 -> PayloadType.MerkleProof (
           leafinfo = decoder.decodeSerializableValue(LeafInfo.serializer()),
     )   3 -> PayloadType.Number (
           u64 = decoder.decodeSerializableValue(kotlin.ULong.serializer()),
     )   else -> { throw Throwable("Can not deserialize")}
    }
}

@Serializable
enum class UseMethod {
    Burn,

    Multiple,

    Single
}
