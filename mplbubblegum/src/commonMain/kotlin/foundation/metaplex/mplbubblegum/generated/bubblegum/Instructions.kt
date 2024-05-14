package foundation.metaplex.mplbubblegum.generated.bubblegum

import com.funkatronics.kborsh.Borsh
import com.solana.publickey.PublicKey
import com.solana.publickey.SolanaPublicKey
import foundation.metaplex.rpc.serializers.AnchorInstructionSerializer
import foundation.metaplex.rpc.serializers.PublicKeyAs32ByteSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import kotlin.Boolean
import kotlin.UByte
import kotlin.UInt
import kotlin.ULong
import kotlin.collections.List
import kotlinx.serialization.Serializable

fun PublicKey(base58: String) = SolanaPublicKey.from(base58)

object BubblegumInstructions {
    fun createTree(
        treeAuthority: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeCreator: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        maxDepth: UInt,
        maxBufferSize: UInt,
        public: Boolean?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, true))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(treeCreator, true, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("create_tree"),
                Args_createTree(maxDepth, maxBufferSize, public)))
    }

    fun setTreeDelegate(
        treeAuthority: PublicKey,
        treeCreator: PublicKey,
        newTreeDelegate: PublicKey,
        merkleTree: PublicKey,
        systemProgram: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, true))
        keys.add(AccountMeta(treeCreator, true, false))
        keys.add(AccountMeta(newTreeDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("set_tree_delegate"),
                Args_setTreeDelegate()))
    }

    fun mintV1(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeDelegate: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        message: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, true))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(treeDelegate, true, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("mint_v1"),
                Args_mintV1(message)))
    }

    fun mintToCollectionV1(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeDelegate: PublicKey,
        collectionAuthority: PublicKey,
        collectionAuthorityRecordPda: PublicKey,
        collectionMint: PublicKey,
        collectionMetadata: PublicKey,
        editionAccount: PublicKey,
        bubblegumSigner: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        tokenMetadataProgram: PublicKey,
        systemProgram: PublicKey,
        metadataArgs: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, true))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(treeDelegate, true, false))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(collectionAuthorityRecordPda, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(editionAccount, false, false))
        keys.add(AccountMeta(bubblegumSigner, false, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("mint_to_collection_v1"),
                Args_mintToCollectionV1(metadataArgs)))
    }

    fun verifyCreator(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        creator: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt,
        message: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(creator, true, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("verify_creator"),
                Args_verifyCreator(root, dataHash, creatorHash, nonce, index, message)))
    }

    fun unverifyCreator(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        creator: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt,
        message: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(creator, true, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("unverify_creator"),
                Args_unverifyCreator(root, dataHash, creatorHash, nonce, index, message)))
    }

    fun verifyCollection(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeDelegate: PublicKey,
        collectionAuthority: PublicKey,
        collectionAuthorityRecordPda: PublicKey,
        collectionMint: PublicKey,
        collectionMetadata: PublicKey,
        editionAccount: PublicKey,
        bubblegumSigner: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        tokenMetadataProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt,
        message: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(treeDelegate, false, false))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(collectionAuthorityRecordPda, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(editionAccount, false, false))
        keys.add(AccountMeta(bubblegumSigner, false, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("verify_collection"),
                Args_verifyCollection(root, dataHash, creatorHash, nonce, index, message)))
    }

    fun unverifyCollection(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeDelegate: PublicKey,
        collectionAuthority: PublicKey,
        collectionAuthorityRecordPda: PublicKey,
        collectionMint: PublicKey,
        collectionMetadata: PublicKey,
        editionAccount: PublicKey,
        bubblegumSigner: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        tokenMetadataProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt,
        message: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(treeDelegate, false, false))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(collectionAuthorityRecordPda, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(editionAccount, false, false))
        keys.add(AccountMeta(bubblegumSigner, false, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("unverify_collection"),
                Args_unverifyCollection(root, dataHash, creatorHash, nonce, index, message)))
    }

    fun setAndVerifyCollection(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        payer: PublicKey,
        treeDelegate: PublicKey,
        collectionAuthority: PublicKey,
        collectionAuthorityRecordPda: PublicKey,
        collectionMint: PublicKey,
        collectionMetadata: PublicKey,
        editionAccount: PublicKey,
        bubblegumSigner: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        tokenMetadataProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt,
        message: MetadataArgs,
        collection: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(treeDelegate, false, false))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(collectionAuthorityRecordPda, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(editionAccount, false, false))
        keys.add(AccountMeta(bubblegumSigner, false, false))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys,
            Borsh.encodeToByteArray(AnchorInstructionSerializer("set_and_verify_collection"),
                Args_setAndVerifyCollection(root, dataHash, creatorHash, nonce, index, message,
                    collection)))
    }

    fun transfer(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        newLeafOwner: PublicKey,
        merkleTree: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(newLeafOwner, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("transfer"),
                Args_transfer(root, dataHash, creatorHash, nonce, index)))
    }

    fun delegate(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        previousLeafDelegate: PublicKey,
        newLeafDelegate: PublicKey,
        merkleTree: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, true, false))
        keys.add(AccountMeta(previousLeafDelegate, false, false))
        keys.add(AccountMeta(newLeafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("delegate"),
                Args_delegate(root, dataHash, creatorHash, nonce, index)))
    }

    fun burn(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, false, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("burn"), Args_burn(root,
                dataHash, creatorHash, nonce, index)))
    }

    fun redeem(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        voucher: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>,
        dataHash: List<UByte>,
        creatorHash: List<UByte>,
        nonce: ULong,
        index: UInt
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, true, true))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(voucher, false, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("redeem"),
                Args_redeem(root, dataHash, creatorHash, nonce, index)))
    }

    fun cancelRedeem(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        merkleTree: PublicKey,
        voucher: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        systemProgram: PublicKey,
        root: List<UByte>
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, true, true))
        keys.add(AccountMeta(merkleTree, false, true))
        keys.add(AccountMeta(voucher, false, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("cancel_redeem"),
                Args_cancelRedeem(root)))
    }

    fun decompressV1(
        voucher: PublicKey,
        leafOwner: PublicKey,
        tokenAccount: PublicKey,
        mint: PublicKey,
        mintAuthority: PublicKey,
        metadataId: PublicKey,
        masterEdition: PublicKey,
        systemProgram: PublicKey,
        sysvarRent: PublicKey,
        tokenMetadataProgram: PublicKey,
        tokenProgram: PublicKey,
        associatedTokenProgram: PublicKey,
        logWrapper: PublicKey,
        metadata: MetadataArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(voucher, false, true))
        keys.add(AccountMeta(leafOwner, true, true))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(mintAuthority, false, true))
        keys.add(AccountMeta(metadataId, false, true))
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarRent, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(associatedTokenProgram, false, false))
        keys.add(AccountMeta(logWrapper, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("decompress_v1"),
                Args_decompressV1(metadata)))
    }

    fun compress(
        treeAuthority: PublicKey,
        leafOwner: PublicKey,
        leafDelegate: PublicKey,
        merkleTree: PublicKey,
        tokenAccount: PublicKey,
        mint: PublicKey,
        metadata: PublicKey,
        masterEdition: PublicKey,
        payer: PublicKey,
        logWrapper: PublicKey,
        compressionProgram: PublicKey,
        tokenProgram: PublicKey,
        tokenMetadataProgram: PublicKey,
        systemProgram: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, false))
        keys.add(AccountMeta(leafOwner, true, false))
        keys.add(AccountMeta(leafDelegate, false, false))
        keys.add(AccountMeta(merkleTree, false, false))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(logWrapper, false, false))
        keys.add(AccountMeta(compressionProgram, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(tokenMetadataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys, Borsh.encodeToByteArray(AnchorInstructionSerializer("compress"),
                Args_compress()))
    }

    fun setDecompressableState(
        treeAuthority: PublicKey,
        treeCreator: PublicKey,
        decompressableState: DecompressableState
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(treeAuthority, false, true))
        keys.add(AccountMeta(treeCreator, true, false))
        return TransactionInstruction(PublicKey("BGUMAp9Gq7iTEuizy4pqaxsTyUCBK68MDfK752saRPUY"),
            keys,
            Borsh.encodeToByteArray(AnchorInstructionSerializer("set_decompressable_state"),
                Args_setDecompressableState(decompressableState)))
    }

    @Serializable
    class Args_createTree(
        val maxDepth: UInt,
        val maxBufferSize: UInt,
        val public: Boolean?
    )

    @Serializable
    class Args_setTreeDelegate()

    @Serializable
    class Args_mintV1(val message: MetadataArgs)

    @Serializable
    class Args_mintToCollectionV1(val metadataArgs: MetadataArgs)

    @Serializable
    class Args_verifyCreator(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt,
        val message: MetadataArgs
    )

    @Serializable
    class Args_unverifyCreator(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt,
        val message: MetadataArgs
    )

    @Serializable
    class Args_verifyCollection(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt,
        val message: MetadataArgs
    )

    @Serializable
    class Args_unverifyCollection(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt,
        val message: MetadataArgs
    )

    @Serializable
    class Args_setAndVerifyCollection(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt,
        val message: MetadataArgs,
        @Serializable(with = PublicKeyAs32ByteSerializer::class) val collection: PublicKey
    )

    @Serializable
    class Args_transfer(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt
    )

    @Serializable
    class Args_delegate(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt
    )

    @Serializable
    class Args_burn(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt
    )

    @Serializable
    class Args_redeem(
        val root: List<UByte>,
        val dataHash: List<UByte>,
        val creatorHash: List<UByte>,
        val nonce: ULong,
        val index: UInt
    )

    @Serializable
    class Args_cancelRedeem(val root: List<UByte>)

    @Serializable
    class Args_decompressV1(val metadata: MetadataArgs)

    @Serializable
    class Args_compress()

    @Serializable
    class Args_setDecompressableState(val decompressableState: DecompressableState)
}