//
// Instructions
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-07-18
//
package foundation.metaplex.mpltokenmetadata.generated

import com.funkatronics.kborsh.Borsh
import foundation.metaplex.rpc.serializers.ByteDiscriminatorSerializer
import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.Serializable

object TokenMetadataInstructions {
    fun CreateMetadataAccount(
        metadata: PublicKey,
        mint: PublicKey,
        mintAuthority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(0),
                Args_CreateMetadataAccount()))
    }

    fun UpdateMetadataAccount(metadata: PublicKey, updateAuthority: PublicKey):
            TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(1),
                Args_UpdateMetadataAccount()))
    }

    fun DeprecatedCreateMasterEdition(
        edition: PublicKey,
        mint: PublicKey,
        printingMint: PublicKey,
        oneTimePrintingAuthorizationMint: PublicKey,
        updateAuthority: PublicKey,
        printingMintAuthority: PublicKey,
        mintAuthority: PublicKey,
        metadata: PublicKey,
        payer: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey,
        oneTimePrintingAuthorizationMintAuthority: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(printingMint, false, true))
        keys.add(AccountMeta(oneTimePrintingAuthorizationMint, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(printingMintAuthority, true, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        keys.add(AccountMeta(oneTimePrintingAuthorizationMintAuthority, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(
                ByteDiscriminatorSerializer(2),
                Args_DeprecatedCreateMasterEdition()))
    }

    fun DeprecatedMintNewEditionFromMasterEditionViaPrintingToken(
        metadata: PublicKey,
        edition: PublicKey,
        masterEdition: PublicKey,
        mint: PublicKey,
        mintAuthority: PublicKey,
        printingMint: PublicKey,
        masterTokenAccount: PublicKey,
        editionMarker: PublicKey,
        burnAuthority: PublicKey,
        payer: PublicKey,
        masterUpdateAuthority: PublicKey,
        masterMetadata: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey,
        reservationList: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(printingMint, false, true))
        keys.add(AccountMeta(masterTokenAccount, false, true))
        keys.add(AccountMeta(editionMarker, false, true))
        keys.add(AccountMeta(burnAuthority, true, false))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(masterUpdateAuthority, false, false))
        keys.add(AccountMeta(masterMetadata, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        reservationList?.let { keys.add(AccountMeta(it, false, true)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(3),
                Args_DeprecatedMintNewEditionFromMasterEditionViaPrintingToken()))
    }

    fun UpdatePrimarySaleHappenedViaToken(
        metadata: PublicKey,
        owner: PublicKey,
        token: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(owner, true, false))
        keys.add(AccountMeta(token, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(4),
                Args_UpdatePrimarySaleHappenedViaToken()))
    }

    fun DeprecatedSetReservationList(
        masterEdition: PublicKey,
        reservationList: PublicKey,
        resource: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(reservationList, false, true))
        keys.add(AccountMeta(resource, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(5),
                Args_DeprecatedSetReservationList()))
    }

    fun DeprecatedCreateReservationList(
        reservationList: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        masterEdition: PublicKey,
        resource: PublicKey,
        metadata: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(reservationList, false, true))
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(masterEdition, false, false))
        keys.add(AccountMeta(resource, false, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(6),
                Args_DeprecatedCreateReservationList()))
    }

    fun SignMetadata(metadata: PublicKey, creator: PublicKey): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(creator, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(7), Args_SignMetadata()))
    }

    fun DeprecatedMintPrintingTokensViaToken(
        destination: PublicKey,
        token: PublicKey,
        oneTimePrintingAuthorizationMint: PublicKey,
        printingMint: PublicKey,
        burnAuthority: PublicKey,
        metadata: PublicKey,
        masterEdition: PublicKey,
        tokenProgram: PublicKey,
        rent: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(destination, false, true))
        keys.add(AccountMeta(token, false, true))
        keys.add(AccountMeta(oneTimePrintingAuthorizationMint, false, true))
        keys.add(AccountMeta(printingMint, false, true))
        keys.add(AccountMeta(burnAuthority, true, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(masterEdition, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(8),
                Args_DeprecatedMintPrintingTokensViaToken()))
    }

    fun DeprecatedMintPrintingTokens(
        destination: PublicKey,
        printingMint: PublicKey,
        updateAuthority: PublicKey,
        metadata: PublicKey,
        masterEdition: PublicKey,
        tokenProgram: PublicKey,
        rent: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(destination, false, true))
        keys.add(AccountMeta(printingMint, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(masterEdition, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(9),
                Args_DeprecatedMintPrintingTokens()))
    }

    fun CreateMasterEdition(
        edition: PublicKey,
        mint: PublicKey,
        updateAuthority: PublicKey,
        mintAuthority: PublicKey,
        payer: PublicKey,
        metadata: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(10),
                Args_CreateMasterEdition()))
    }

    fun MintNewEditionFromMasterEditionViaToken(
        newMetadata: PublicKey,
        newEdition: PublicKey,
        masterEdition: PublicKey,
        newMint: PublicKey,
        editionMarkPda: PublicKey,
        newMintAuthority: PublicKey,
        payer: PublicKey,
        tokenAccountOwner: PublicKey,
        tokenAccount: PublicKey,
        newMetadataUpdateAuthority: PublicKey,
        metadata: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?,
        mintNewEditionFromMasterEditionViaTokenArgs: MintNewEditionFromMasterEditionViaTokenArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(newMetadata, false, true))
        keys.add(AccountMeta(newEdition, false, true))
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(newMint, false, true))
        keys.add(AccountMeta(editionMarkPda, false, true))
        keys.add(AccountMeta(newMintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(tokenAccountOwner, true, false))
        keys.add(AccountMeta(tokenAccount, false, false))
        keys.add(AccountMeta(newMetadataUpdateAuthority, false, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(11),
                Args_MintNewEditionFromMasterEditionViaToken(mintNewEditionFromMasterEditionViaTokenArgs)))
    }

    fun ConvertMasterEditionV1ToV2(
        masterEdition: PublicKey,
        oneTimeAuth: PublicKey,
        printingMint: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(oneTimeAuth, false, true))
        keys.add(AccountMeta(printingMint, false, true))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(12),
                Args_ConvertMasterEditionV1ToV2()))
    }

    fun MintNewEditionFromMasterEditionViaVaultProxy(
        newMetadata: PublicKey,
        newEdition: PublicKey,
        masterEdition: PublicKey,
        newMint: PublicKey,
        editionMarkPda: PublicKey,
        newMintAuthority: PublicKey,
        payer: PublicKey,
        vaultAuthority: PublicKey,
        safetyDepositStore: PublicKey,
        safetyDepositBox: PublicKey,
        vault: PublicKey,
        newMetadataUpdateAuthority: PublicKey,
        metadata: PublicKey,
        tokenProgram: PublicKey,
        tokenVaultProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?,
        mintNewEditionFromMasterEditionViaTokenArgs: MintNewEditionFromMasterEditionViaTokenArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(newMetadata, false, true))
        keys.add(AccountMeta(newEdition, false, true))
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(newMint, false, true))
        keys.add(AccountMeta(editionMarkPda, false, true))
        keys.add(AccountMeta(newMintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(vaultAuthority, true, false))
        keys.add(AccountMeta(safetyDepositStore, false, false))
        keys.add(AccountMeta(safetyDepositBox, false, false))
        keys.add(AccountMeta(vault, false, false))
        keys.add(AccountMeta(newMetadataUpdateAuthority, false, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(tokenVaultProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(13),
                Args_MintNewEditionFromMasterEditionViaVaultProxy(mintNewEditionFromMasterEditionViaTokenArgs)))
    }

    fun PuffMetadata(metadata: PublicKey): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(14), Args_PuffMetadata()))
    }

    fun UpdateMetadataAccountV2(
        metadata: PublicKey,
        updateAuthority: PublicKey,
        updateMetadataAccountArgsV2: UpdateMetadataAccountArgsV2
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(15),
                Args_UpdateMetadataAccountV2(updateMetadataAccountArgsV2)))
    }

    fun CreateMetadataAccountV2(
        metadata: PublicKey,
        mint: PublicKey,
        mintAuthority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(16),
                Args_CreateMetadataAccountV2()))
    }

    fun CreateMasterEditionV3(
        edition: PublicKey,
        mint: PublicKey,
        updateAuthority: PublicKey,
        mintAuthority: PublicKey,
        payer: PublicKey,
        metadata: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?,
        createMasterEditionArgs: CreateMasterEditionArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(17),
                Args_CreateMasterEditionV3(createMasterEditionArgs)))
    }

    fun VerifyCollection(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        payer: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, false))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(18),
                Args_VerifyCollection()))
    }

    fun Utilize(
        metadata: PublicKey,
        tokenAccount: PublicKey,
        mint: PublicKey,
        useAuthority: PublicKey,
        owner: PublicKey,
        tokenProgram: PublicKey,
        ataProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey,
        useAuthorityRecord: PublicKey?,
        burner: PublicKey?,
        utilizeArgs: UtilizeArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(useAuthority, true, true))
        keys.add(AccountMeta(owner, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(ataProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(rent, false, false))
        useAuthorityRecord?.let { keys.add(AccountMeta(it, false, true)) }
        burner?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(19),
                Args_Utilize(utilizeArgs)))
    }

    fun ApproveUseAuthority(
        useAuthorityRecord: PublicKey,
        owner: PublicKey,
        payer: PublicKey,
        user: PublicKey,
        ownerTokenAccount: PublicKey,
        metadata: PublicKey,
        mint: PublicKey,
        burner: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?,
        approveUseAuthorityArgs: ApproveUseAuthorityArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(useAuthorityRecord, false, true))
        keys.add(AccountMeta(owner, true, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(user, false, false))
        keys.add(AccountMeta(ownerTokenAccount, false, true))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(burner, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(20),
                Args_ApproveUseAuthority(approveUseAuthorityArgs)))
    }

    fun RevokeUseAuthority(
        useAuthorityRecord: PublicKey,
        owner: PublicKey,
        user: PublicKey,
        ownerTokenAccount: PublicKey,
        mint: PublicKey,
        metadata: PublicKey,
        tokenProgram: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(useAuthorityRecord, false, true))
        keys.add(AccountMeta(owner, true, true))
        keys.add(AccountMeta(user, false, false))
        keys.add(AccountMeta(ownerTokenAccount, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(21),
                Args_RevokeUseAuthority()))
    }

    fun UnverifyCollection(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, false))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(22),
                Args_UnverifyCollection()))
    }

    fun ApproveCollectionAuthority(
        collectionAuthorityRecord: PublicKey,
        newCollectionAuthority: PublicKey,
        updateAuthority: PublicKey,
        payer: PublicKey,
        metadata: PublicKey,
        mint: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(collectionAuthorityRecord, false, true))
        keys.add(AccountMeta(newCollectionAuthority, false, false))
        keys.add(AccountMeta(updateAuthority, true, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(23),
                Args_ApproveCollectionAuthority()))
    }

    fun RevokeCollectionAuthority(
        collectionAuthorityRecord: PublicKey,
        delegateAuthority: PublicKey,
        revokeAuthority: PublicKey,
        metadata: PublicKey,
        mint: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(collectionAuthorityRecord, false, true))
        keys.add(AccountMeta(delegateAuthority, false, true))
        keys.add(AccountMeta(revokeAuthority, true, true))
        keys.add(AccountMeta(metadata, false, false))
        keys.add(AccountMeta(mint, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(24),
                Args_RevokeCollectionAuthority()))
    }

    fun SetAndVerifyCollection(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, false))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(25),
                Args_SetAndVerifyCollection()))
    }

    fun FreezeDelegatedAccount(
        delegate: PublicKey,
        tokenAccount: PublicKey,
        edition: PublicKey,
        mint: PublicKey,
        tokenProgram: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(delegate, true, true))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(edition, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(26),
                Args_FreezeDelegatedAccount()))
    }

    fun ThawDelegatedAccount(
        delegate: PublicKey,
        tokenAccount: PublicKey,
        edition: PublicKey,
        mint: PublicKey,
        tokenProgram: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(delegate, true, true))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(edition, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(27),
                Args_ThawDelegatedAccount()))
    }

    fun RemoveCreatorVerification(metadata: PublicKey, creator: PublicKey): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(creator, true, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(28),
                Args_RemoveCreatorVerification()))
    }

    fun BurnNft(
        metadata: PublicKey,
        owner: PublicKey,
        mint: PublicKey,
        tokenAccount: PublicKey,
        masterEditionAccount: PublicKey,
        splTokenProgram: PublicKey,
        collectionMetadata: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(owner, true, true))
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(tokenAccount, false, true))
        keys.add(AccountMeta(masterEditionAccount, false, true))
        keys.add(AccountMeta(splTokenProgram, false, false))
        collectionMetadata?.let { keys.add(AccountMeta(it, false, true)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(29), Args_BurnNft()))
    }

    fun VerifySizedCollectionItem(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        payer: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, true))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(30),
                Args_VerifySizedCollectionItem()))
    }

    fun UnverifySizedCollectionItem(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        payer: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, true))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(31),
                Args_UnverifySizedCollectionItem()))
    }

    fun SetAndVerifySizedCollectionItem(
        metadata: PublicKey,
        collectionAuthority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        collectionMint: PublicKey,
        collection: PublicKey,
        collectionMasterEditionAccount: PublicKey,
        collectionAuthorityRecord: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(collection, false, true))
        keys.add(AccountMeta(collectionMasterEditionAccount, false, true))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(32),
                Args_SetAndVerifySizedCollectionItem()))
    }

    fun CreateMetadataAccountV3(
        metadata: PublicKey,
        mint: PublicKey,
        mintAuthority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        systemProgram: PublicKey,
        rent: PublicKey?,
        createMetadataAccountArgsV3: CreateMetadataAccountArgsV3
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(mintAuthority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        rent?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(33),
                Args_CreateMetadataAccountV3(createMetadataAccountArgsV3)))
    }

    fun SetCollectionSize(
        collectionMetadata: PublicKey,
        collectionAuthority: PublicKey,
        collectionMint: PublicKey,
        collectionAuthorityRecord: PublicKey?,
        setCollectionSizeArgs: SetCollectionSizeArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(34),
                Args_SetCollectionSize(setCollectionSizeArgs)))
    }

    fun SetTokenStandard(
        metadata: PublicKey,
        updateAuthority: PublicKey,
        mint: PublicKey,
        edition: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(updateAuthority, true, false))
        keys.add(AccountMeta(mint, false, false))
        edition?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(35),
                Args_SetTokenStandard()))
    }

    fun BubblegumSetCollectionSize(
        collectionMetadata: PublicKey,
        collectionAuthority: PublicKey,
        collectionMint: PublicKey,
        bubblegumSigner: PublicKey,
        collectionAuthorityRecord: PublicKey?,
        setCollectionSizeArgs: SetCollectionSizeArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(collectionMetadata, false, true))
        keys.add(AccountMeta(collectionAuthority, true, true))
        keys.add(AccountMeta(collectionMint, false, false))
        keys.add(AccountMeta(bubblegumSigner, true, false))
        collectionAuthorityRecord?.let { keys.add(AccountMeta(it, false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(36),
                Args_BubblegumSetCollectionSize(setCollectionSizeArgs)))
    }

    fun BurnEditionNft(
        metadata: PublicKey,
        owner: PublicKey,
        printEditionMint: PublicKey,
        masterEditionMint: PublicKey,
        printEditionTokenAccount: PublicKey,
        masterEditionTokenAccount: PublicKey,
        masterEditionAccount: PublicKey,
        printEditionAccount: PublicKey,
        editionMarkerAccount: PublicKey,
        splTokenProgram: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(owner, true, true))
        keys.add(AccountMeta(printEditionMint, false, true))
        keys.add(AccountMeta(masterEditionMint, false, false))
        keys.add(AccountMeta(printEditionTokenAccount, false, true))
        keys.add(AccountMeta(masterEditionTokenAccount, false, false))
        keys.add(AccountMeta(masterEditionAccount, false, true))
        keys.add(AccountMeta(printEditionAccount, false, true))
        keys.add(AccountMeta(editionMarkerAccount, false, true))
        keys.add(AccountMeta(splTokenProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(37),
                Args_BurnEditionNft()))
    }

    fun CreateEscrowAccount(
        escrow: PublicKey,
        metadata: PublicKey,
        mint: PublicKey,
        tokenAccount: PublicKey,
        edition: PublicKey,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        authority: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(escrow, false, true))
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(tokenAccount, false, false))
        keys.add(AccountMeta(edition, false, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        authority?.let { keys.add(AccountMeta(it, true, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(38),
                Args_CreateEscrowAccount()))
    }

    fun CloseEscrowAccount(
        escrow: PublicKey,
        metadata: PublicKey,
        mint: PublicKey,
        tokenAccount: PublicKey,
        edition: PublicKey,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(escrow, false, true))
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(tokenAccount, false, false))
        keys.add(AccountMeta(edition, false, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(39),
                Args_CloseEscrowAccount()))
    }

    fun TransferOutOfEscrow(
        escrow: PublicKey,
        metadata: PublicKey,
        payer: PublicKey,
        attributeMint: PublicKey,
        attributeSrc: PublicKey,
        attributeDst: PublicKey,
        escrowMint: PublicKey,
        escrowAccount: PublicKey,
        systemProgram: PublicKey,
        ataProgram: PublicKey,
        tokenProgram: PublicKey,
        sysvarInstructions: PublicKey,
        authority: PublicKey?,
        transferOutOfEscrowArgs: TransferOutOfEscrowArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(escrow, false, false))
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(attributeMint, false, false))
        keys.add(AccountMeta(attributeSrc, false, true))
        keys.add(AccountMeta(attributeDst, false, true))
        keys.add(AccountMeta(escrowMint, false, false))
        keys.add(AccountMeta(escrowAccount, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(ataProgram, false, false))
        keys.add(AccountMeta(tokenProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        authority?.let { keys.add(AccountMeta(it, true, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(40),
                Args_TransferOutOfEscrow(transferOutOfEscrowArgs)))
    }

    fun Burn(
        authority: PublicKey,
        collectionMetadata: PublicKey?,
        metadata: PublicKey,
        edition: PublicKey?,
        mint: PublicKey,
        token: PublicKey,
        masterEdition: PublicKey?,
        masterEditionMint: PublicKey?,
        masterEditionToken: PublicKey?,
        editionMarker: PublicKey?,
        tokenRecord: PublicKey?,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey,
        burnArgs: BurnArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, true))
        collectionMetadata?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(token, false, true))
        masterEdition?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        masterEditionMint?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        masterEditionToken?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        editionMarker?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(41), Args_Burn(burnArgs)))
    }

    fun Create(
        metadata: PublicKey,
        masterEdition: PublicKey?,
        mint: PublicKey,
        authority: PublicKey,
        payer: PublicKey,
        updateAuthority: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey,
        createArgs: CreateArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        masterEdition?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(42),
                Args_Create(createArgs)))
    }

    fun Mint(
        token: PublicKey,
        tokenOwner: PublicKey?,
        metadata: PublicKey,
        masterEdition: PublicKey?,
        tokenRecord: PublicKey?,
        mint: PublicKey,
        authority: PublicKey,
        delegateRecord: PublicKey?,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey,
        splAtaProgram: PublicKey,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        mintArgs: MintArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(token, false, true))
        tokenOwner?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(metadata, false, false))
        masterEdition?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, true))
        keys.add(AccountMeta(authority, true, false))
        delegateRecord?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        keys.add(AccountMeta(splAtaProgram, false, false))
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(43), Args_Mint(mintArgs)))
    }

    fun Delegate(
        delegateRecord: PublicKey?,
        delegate: PublicKey,
        metadata: PublicKey,
        masterEdition: PublicKey?,
        tokenRecord: PublicKey?,
        mint: PublicKey,
        token: PublicKey?,
        authority: PublicKey,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey?,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        delegateArgs: DelegateArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        delegateRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(delegate, false, false))
        keys.add(AccountMeta(metadata, false, true))
        masterEdition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, false))
        token?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        splTokenProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(44),
                Args_Delegate(delegateArgs)))
    }

    fun Revoke(
        delegateRecord: PublicKey?,
        delegate: PublicKey,
        metadata: PublicKey,
        masterEdition: PublicKey?,
        tokenRecord: PublicKey?,
        mint: PublicKey,
        token: PublicKey?,
        authority: PublicKey,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey?,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        revokeArgs: RevokeArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        delegateRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(delegate, false, false))
        keys.add(AccountMeta(metadata, false, true))
        masterEdition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, false))
        token?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        splTokenProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(45),
                Args_Revoke(revokeArgs)))
    }

    fun Lock(
        authority: PublicKey,
        tokenOwner: PublicKey?,
        token: PublicKey,
        mint: PublicKey,
        metadata: PublicKey,
        edition: PublicKey?,
        tokenRecord: PublicKey?,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey?,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        lockArgs: LockArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        tokenOwner?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(token, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        splTokenProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(46), Args_Lock(lockArgs)))
    }

    fun Unlock(
        authority: PublicKey,
        tokenOwner: PublicKey?,
        token: PublicKey,
        mint: PublicKey,
        metadata: PublicKey,
        edition: PublicKey?,
        tokenRecord: PublicKey?,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey?,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        unlockArgs: UnlockArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        tokenOwner?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(token, false, true))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        tokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        splTokenProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(47),
                Args_Unlock(unlockArgs)))
    }

    fun Migrate(
        metadata: PublicKey,
        edition: PublicKey,
        token: PublicKey,
        tokenOwner: PublicKey,
        mint: PublicKey,
        payer: PublicKey,
        authority: PublicKey,
        collectionMetadata: PublicKey,
        delegateRecord: PublicKey,
        tokenRecord: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(metadata, false, true))
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(token, false, true))
        keys.add(AccountMeta(tokenOwner, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(collectionMetadata, false, false))
        keys.add(AccountMeta(delegateRecord, false, false))
        keys.add(AccountMeta(tokenRecord, false, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(48), Args_Migrate()))
    }

    fun Transfer(
        token: PublicKey,
        tokenOwner: PublicKey,
        destination: PublicKey,
        destinationOwner: PublicKey,
        mint: PublicKey,
        metadata: PublicKey,
        edition: PublicKey?,
        ownerTokenRecord: PublicKey?,
        destinationTokenRecord: PublicKey?,
        authority: PublicKey,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey,
        splAtaProgram: PublicKey,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        transferArgs: TransferArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(token, false, true))
        keys.add(AccountMeta(tokenOwner, false, false))
        keys.add(AccountMeta(destination, false, true))
        keys.add(AccountMeta(destinationOwner, false, false))
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        ownerTokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        destinationTokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        keys.add(AccountMeta(splAtaProgram, false, false))
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(49),
                Args_Transfer(transferArgs)))
    }

    fun Update(
        authority: PublicKey,
        delegateRecord: PublicKey?,
        token: PublicKey?,
        mint: PublicKey,
        metadata: PublicKey,
        edition: PublicKey?,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        updateArgs: UpdateArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        delegateRecord?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        token?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(50),
                Args_Update(updateArgs)))
    }

    fun Use(
        authority: PublicKey,
        delegateRecord: PublicKey?,
        token: PublicKey?,
        mint: PublicKey,
        metadata: PublicKey,
        edition: PublicKey?,
        payer: PublicKey,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        splTokenProgram: PublicKey?,
        authorizationRulesProgram: PublicKey?,
        authorizationRules: PublicKey?,
        useArgs: UseArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        delegateRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        token?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(mint, false, false))
        keys.add(AccountMeta(metadata, false, true))
        edition?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(payer, true, false))
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        splTokenProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRulesProgram?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        authorizationRules?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(51), Args_Use(useArgs)))
    }

    fun Verify(
        authority: PublicKey,
        delegateRecord: PublicKey?,
        metadata: PublicKey,
        collectionMint: PublicKey?,
        collectionMetadata: PublicKey?,
        collectionMasterEdition: PublicKey?,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        verificationArgs: VerificationArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        delegateRecord?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(metadata, false, true))
        collectionMint?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        collectionMetadata?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        collectionMasterEdition?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(52),
                Args_Verify(verificationArgs)))
    }

    fun Unverify(
        authority: PublicKey,
        delegateRecord: PublicKey?,
        metadata: PublicKey,
        collectionMint: PublicKey?,
        collectionMetadata: PublicKey?,
        systemProgram: PublicKey,
        sysvarInstructions: PublicKey,
        verificationArgs: VerificationArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        delegateRecord?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(metadata, false, true))
        collectionMint?.let { keys.add(AccountMeta(it, false, false)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        collectionMetadata?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(systemProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(53),
                Args_Unverify(verificationArgs)))
    }

    fun Collect(authority: PublicKey, pdaAccount: PublicKey): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(authority, true, false))
        keys.add(AccountMeta(pdaAccount, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(54), Args_Collect()))
    }

    fun Print(
        editionMetadata: PublicKey,
        edition: PublicKey,
        editionMint: PublicKey,
        editionTokenAccountOwner: PublicKey,
        editionTokenAccount: PublicKey,
        editionMintAuthority: PublicKey,
        editionTokenRecord: PublicKey?,
        masterEdition: PublicKey,
        editionMarkerPda: PublicKey,
        payer: PublicKey,
        masterTokenAccountOwner: PublicKey,
        masterTokenAccount: PublicKey,
        masterMetadata: PublicKey,
        updateAuthority: PublicKey,
        splTokenProgram: PublicKey,
        splAtaProgram: PublicKey,
        sysvarInstructions: PublicKey,
        systemProgram: PublicKey,
        printArgs: PrintArgs
    ): TransactionInstruction {
        val keys = mutableListOf<AccountMeta>()
        keys.add(AccountMeta(editionMetadata, false, true))
        keys.add(AccountMeta(edition, false, true))
        keys.add(AccountMeta(editionMint, false, true))
        keys.add(AccountMeta(editionTokenAccountOwner, false, false))
        keys.add(AccountMeta(editionTokenAccount, false, true))
        keys.add(AccountMeta(editionMintAuthority, true, false))
        editionTokenRecord?.let { keys.add(AccountMeta(it, false, true)) }
              ?: run {
                keys.add(AccountMeta(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                false, false)) }
        keys.add(AccountMeta(masterEdition, false, true))
        keys.add(AccountMeta(editionMarkerPda, false, true))
        keys.add(AccountMeta(payer, true, true))
        keys.add(AccountMeta(masterTokenAccountOwner, true, false))
        keys.add(AccountMeta(masterTokenAccount, false, false))
        keys.add(AccountMeta(masterMetadata, false, false))
        keys.add(AccountMeta(updateAuthority, false, false))
        keys.add(AccountMeta(splTokenProgram, false, false))
        keys.add(AccountMeta(splAtaProgram, false, false))
        keys.add(AccountMeta(sysvarInstructions, false, false))
        keys.add(AccountMeta(systemProgram, false, false))
        return TransactionInstruction(PublicKey("metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"),
                keys, Borsh.encodeToByteArray(ByteDiscriminatorSerializer(55),
                Args_Print(printArgs)))
    }

    @Serializable
    class Args_CreateMetadataAccount()

    @Serializable
    class Args_UpdateMetadataAccount()

    @Serializable
    class Args_DeprecatedCreateMasterEdition()

    @Serializable
    class Args_DeprecatedMintNewEditionFromMasterEditionViaPrintingToken()

    @Serializable
    class Args_UpdatePrimarySaleHappenedViaToken()

    @Serializable
    class Args_DeprecatedSetReservationList()

    @Serializable
    class Args_DeprecatedCreateReservationList()

    @Serializable
    class Args_SignMetadata()

    @Serializable
    class Args_DeprecatedMintPrintingTokensViaToken()

    @Serializable
    class Args_DeprecatedMintPrintingTokens()

    @Serializable
    class Args_CreateMasterEdition()

    @Serializable
    class Args_MintNewEditionFromMasterEditionViaToken(val
            mintNewEditionFromMasterEditionViaTokenArgs:
            MintNewEditionFromMasterEditionViaTokenArgs)

    @Serializable
    class Args_ConvertMasterEditionV1ToV2()

    @Serializable
    class Args_MintNewEditionFromMasterEditionViaVaultProxy(val
            mintNewEditionFromMasterEditionViaTokenArgs:
            MintNewEditionFromMasterEditionViaTokenArgs)

    @Serializable
    class Args_PuffMetadata()

    @Serializable
    class Args_UpdateMetadataAccountV2(val updateMetadataAccountArgsV2: UpdateMetadataAccountArgsV2)

    @Serializable
    class Args_CreateMetadataAccountV2()

    @Serializable
    class Args_CreateMasterEditionV3(val createMasterEditionArgs: CreateMasterEditionArgs)

    @Serializable
    class Args_VerifyCollection()

    @Serializable
    class Args_Utilize(val utilizeArgs: UtilizeArgs)

    @Serializable
    class Args_ApproveUseAuthority(val approveUseAuthorityArgs: ApproveUseAuthorityArgs)

    @Serializable
    class Args_RevokeUseAuthority()

    @Serializable
    class Args_UnverifyCollection()

    @Serializable
    class Args_ApproveCollectionAuthority()

    @Serializable
    class Args_RevokeCollectionAuthority()

    @Serializable
    class Args_SetAndVerifyCollection()

    @Serializable
    class Args_FreezeDelegatedAccount()

    @Serializable
    class Args_ThawDelegatedAccount()

    @Serializable
    class Args_RemoveCreatorVerification()

    @Serializable
    class Args_BurnNft()

    @Serializable
    class Args_VerifySizedCollectionItem()

    @Serializable
    class Args_UnverifySizedCollectionItem()

    @Serializable
    class Args_SetAndVerifySizedCollectionItem()

    @Serializable
    class Args_CreateMetadataAccountV3(val createMetadataAccountArgsV3: CreateMetadataAccountArgsV3)

    @Serializable
    class Args_SetCollectionSize(val setCollectionSizeArgs: SetCollectionSizeArgs)

    @Serializable
    class Args_SetTokenStandard()

    @Serializable
    class Args_BubblegumSetCollectionSize(val setCollectionSizeArgs: SetCollectionSizeArgs)

    @Serializable
    class Args_BurnEditionNft()

    @Serializable
    class Args_CreateEscrowAccount()

    @Serializable
    class Args_CloseEscrowAccount()

    @Serializable
    class Args_TransferOutOfEscrow(val transferOutOfEscrowArgs: TransferOutOfEscrowArgs)

    @Serializable
    class Args_Burn(val burnArgs: BurnArgs)

    @Serializable
    class Args_Create(val createArgs: CreateArgs)

    @Serializable
    class Args_Mint(val mintArgs: MintArgs)

    @Serializable
    class Args_Delegate(val delegateArgs: DelegateArgs)

    @Serializable
    class Args_Revoke(val revokeArgs: RevokeArgs)

    @Serializable
    class Args_Lock(val lockArgs: LockArgs)

    @Serializable
    class Args_Unlock(val unlockArgs: UnlockArgs)

    @Serializable
    class Args_Migrate()

    @Serializable
    class Args_Transfer(val transferArgs: TransferArgs)

    @Serializable
    class Args_Update(val updateArgs: UpdateArgs)

    @Serializable
    class Args_Use(val useArgs: UseArgs)

    @Serializable
    class Args_Verify(val verificationArgs: VerificationArgs)

    @Serializable
    class Args_Unverify(val verificationArgs: VerificationArgs)

    @Serializable
    class Args_Collect()

    @Serializable
    class Args_Print(val printArgs: PrintArgs)
}
