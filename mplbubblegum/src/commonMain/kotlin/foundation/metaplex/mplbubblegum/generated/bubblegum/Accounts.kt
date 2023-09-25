@file:UseSerializers(PublicKeyAs32ByteSerializer::class)

package foundation.metaplex.mplbubblegum.generated.bubblegum

import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.Boolean
import kotlin.UInt
import kotlin.ULong
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
class TreeConfig(
    val treeCreator: PublicKey,
    val treeDelegate: PublicKey,
    val totalMintCapacity: ULong,
    val numMinted: ULong,
    val isPublic: Boolean,
    val isDecompressable: DecompressableState
)

@Serializable
class Voucher(
    val leafSchema: LeafSchema,
    val index: UInt,
    val merkleTree: PublicKey
)
