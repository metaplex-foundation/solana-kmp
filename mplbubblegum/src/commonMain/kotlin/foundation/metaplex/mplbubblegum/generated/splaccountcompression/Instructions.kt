//
// Instructions
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-09-11
//
package foundation.metaplex.mplbubblegum.generated.splaccountcompression

import com.funkatronics.kborsh.Borsh
import foundation.metaplex.rpc.serializers.AnchorInstructionSerializer
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.UByte
import kotlin.UInt
import kotlin.collections.List
import kotlinx.serialization.Serializable

object SPLAccountCompressionInstructions {
    fun initEmptyMerkleTree(
        merkleTree: PublicKey,
        authority: PublicKey,
        noop: PublicKey,
        maxDepth: UInt,
        maxBufferSize: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(noop, false, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(
                AnchorInstructionSerializer("init_empty_merkle_tree"),
                Args_initEmptyMerkleTree(maxDepth, maxBufferSize)))
    }

    fun replaceLeaf(
        merkleTree: PublicKey,
        authority: PublicKey,
        noop: PublicKey,
        root: List<UByte>,
        previousLeaf: List<UByte>,
        newLeaf: List<UByte>,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(noop, false, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("replace_leaf"),
                Args_replaceLeaf(root, previousLeaf, newLeaf, index)))
    }

    fun transferAuthority(
        merkleTree: PublicKey,
        authority: PublicKey,
        newAuthority: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("transfer_authority"),
                Args_transferAuthority(newAuthority)))
    }

    fun verifyLeaf(
        merkleTree: PublicKey,
        root: List<UByte>,
        leaf: List<UByte>,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("verify_leaf"),
                Args_verifyLeaf(root, leaf, index)))
    }

    fun append(
        merkleTree: PublicKey,
        authority: PublicKey,
        noop: PublicKey,
        leaf: List<UByte>
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(noop, false, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("append"),
                Args_append(leaf)))
    }

    fun insertOrAppend(
        merkleTree: PublicKey,
        authority: PublicKey,
        noop: PublicKey,
        root: List<UByte>,
        leaf: List<UByte>,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(noop, false, false))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("insert_or_append"),
                Args_insertOrAppend(root, leaf, index)))
    }

    fun closeEmptyTree(
        merkleTree: PublicKey,
        authority: PublicKey,
        recipient: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(recipient, false, true))
        return TransactionInstruction(PublicKey("cmtDvXumGCrqC1Age74AVPhSRVXJMd8PJS91L8KbNCK"),
                keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("close_empty_tree"),
                Args_closeEmptyTree()))
    }

    @Serializable
    class Args_initEmptyMerkleTree(val maxDepth: UInt, val maxBufferSize: UInt)

    @Serializable
    class Args_replaceLeaf(
        val root: List<UByte>,
        val previousLeaf: List<UByte>,
        val newLeaf: List<UByte>,
        val index: UInt
    )

    @Serializable
    class Args_transferAuthority(@Serializable(with = PublicKeyAs32ByteSerializer::class) val
            newAuthority: PublicKey)

    @Serializable
    class Args_verifyLeaf(
        val root: List<UByte>,
        val leaf: List<UByte>,
        val index: UInt
    )

    @Serializable
    class Args_append(val leaf: List<UByte>)

    @Serializable
    class Args_insertOrAppend(
        val root: List<UByte>,
        val leaf: List<UByte>,
        val index: UInt
    )

    @Serializable
    class Args_closeEmptyTree()
}
