//
// Accounts
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
import kotlin.UByte
import kotlin.ULong
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
class CollectionAuthorityRecord(
    val key: Key,
    val bump: UByte,
    val updateAuthority: PublicKey?
)

@Serializable
class MetadataDelegateRecord(
    val key: Key,
    val bump: UByte,
    val mint: PublicKey,
    val delegate: PublicKey,
    val updateAuthority: PublicKey
)

@Serializable
class Edition(
    val key: Key,
    val parent: PublicKey,
    val edition: ULong
)

@Serializable
class EditionMarker(val key: Key, val ledger: List<UByte>)

@Serializable
class EditionMarkerV2(val key: Key, val ledger: ByteArray)

@Serializable
class TokenOwnedEscrow(
    val key: Key,
    val baseToken: PublicKey,
    val authority: EscrowAuthority,
    val bump: UByte
)

@Serializable
class MasterEditionV2(
    val key: Key,
    val supply: ULong,
    val maxSupply: ULong?
)

@Serializable
class MasterEditionV1(
    val key: Key,
    val supply: ULong,
    val maxSupply: ULong?,
    val printingMint: PublicKey,
    val oneTimePrintingAuthorizationMint: PublicKey
)

@Serializable
class Metadata(
    val key: Key,
    val updateAuthority: PublicKey,
    val mint: PublicKey,
    val data: Data,
    val primarySaleHappened: Boolean,
    val isMutable: Boolean,
    val editionNonce: UByte?,
    val tokenStandard: TokenStandard?,
    val collection: Collection?,
    val uses: Uses?,
    val collectionDetails: CollectionDetails?,
    val programmableConfig: ProgrammableConfig?
)

@Serializable
class TokenRecord(
    val key: Key,
    val bump: UByte,
    val state: TokenState,
    val ruleSetRevision: ULong?,
    val delegate: PublicKey?,
    val delegateRole: TokenDelegateRole?,
    val lockedTransfer: PublicKey?
)

@Serializable
class ReservationListV2(
    val key: Key,
    val masterEdition: PublicKey,
    val supplySnapshot: ULong?,
    val reservations: List<Reservation>,
    val totalReservationSpots: ULong,
    val currentReservationSpots: ULong
)

@Serializable
class ReservationListV1(
    val key: Key,
    val masterEdition: PublicKey,
    val supplySnapshot: ULong?,
    val reservations: List<ReservationV1>
)

@Serializable
class UseAuthorityRecord(
    val key: Key,
    val allowedUses: ULong,
    val bump: UByte
)
