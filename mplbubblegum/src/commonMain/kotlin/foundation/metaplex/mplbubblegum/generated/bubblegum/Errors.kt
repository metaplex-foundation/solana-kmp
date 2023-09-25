package foundation.metaplex.mplbubblegum.generated.bubblegum

import kotlin.Int
import kotlin.String

sealed interface BubblegumError {
    val code: Int

    val message: String
}

class AssetOwnerMismatch : BubblegumError {
    override val code: Int = 6000

    override val message: String = "Asset Owner Does not match"
}

class PublicKeyMismatch : BubblegumError {
    override val code: Int = 6001

    override val message: String = "PublicKeyMismatch"
}

class HashingMismatch : BubblegumError {
    override val code: Int = 6002

    override val message: String = "Hashing Mismatch Within Leaf Schema"
}

class UnsupportedSchemaVersion : BubblegumError {
    override val code: Int = 6003

    override val message: String = "Unsupported Schema Version"
}

class CreatorShareTotalMustBe100 : BubblegumError {
    override val code: Int = 6004

    override val message: String = "Creator shares must sum to 100"
}

class DuplicateCreatorAddress : BubblegumError {
    override val code: Int = 6005

    override val message: String = "No duplicate creator addresses in metadata"
}

class CreatorDidNotVerify : BubblegumError {
    override val code: Int = 6006

    override val message: String = "Creator did not verify the metadata"
}

class CreatorNotFound : BubblegumError {
    override val code: Int = 6007

    override val message: String = "Creator not found in creator Vec"
}

class NoCreatorsPresent : BubblegumError {
    override val code: Int = 6008

    override val message: String = "No creators in creator Vec"
}

class CreatorHashMismatch : BubblegumError {
    override val code: Int = 6009

    override val message: String =
        "User-provided creator Vec must result in same user-provided creator hash"
}

class DataHashMismatch : BubblegumError {
    override val code: Int = 6010

    override val message: String =
        "User-provided metadata must result in same user-provided data hash"
}

class CreatorsTooLong : BubblegumError {
    override val code: Int = 6011

    override val message: String = "Creators list too long"
}

class MetadataNameTooLong : BubblegumError {
    override val code: Int = 6012

    override val message: String = "Name in metadata is too long"
}

class MetadataSymbolTooLong : BubblegumError {
    override val code: Int = 6013

    override val message: String = "Symbol in metadata is too long"
}

class MetadataUriTooLong : BubblegumError {
    override val code: Int = 6014

    override val message: String = "Uri in metadata is too long"
}

class MetadataBasisPointsTooHigh : BubblegumError {
    override val code: Int = 6015

    override val message: String = "Basis points in metadata cannot exceed 10000"
}

class TreeAuthorityIncorrect : BubblegumError {
    override val code: Int = 6016

    override val message: String = "Tree creator or tree delegate must sign."
}

class InsufficientMintCapacity : BubblegumError {
    override val code: Int = 6017

    override val message: String = "Not enough unapproved mints left"
}

class NumericalOverflowError : BubblegumError {
    override val code: Int = 6018

    override val message: String = "NumericalOverflowError"
}

class IncorrectOwner : BubblegumError {
    override val code: Int = 6019

    override val message: String = "Incorrect account owner"
}

class CollectionCannotBeVerifiedInThisInstruction : BubblegumError {
    override val code: Int = 6020

    override val message: String = "Cannot Verify Collection in this Instruction"
}

class CollectionNotFound : BubblegumError {
    override val code: Int = 6021

    override val message: String = "Collection Not Found on Metadata"
}

class AlreadyVerified : BubblegumError {
    override val code: Int = 6022

    override val message: String = "Collection item is already verified."
}

class AlreadyUnverified : BubblegumError {
    override val code: Int = 6023

    override val message: String = "Collection item is already unverified."
}

class UpdateAuthorityIncorrect : BubblegumError {
    override val code: Int = 6024

    override val message: String = "Incorrect leaf metadata update authority."
}

class LeafAuthorityMustSign : BubblegumError {
    override val code: Int = 6025

    override val message: String =
        "This transaction must be signed by either the leaf owner or leaf delegate"
}

class CollectionMustBeSized : BubblegumError {
    override val code: Int = 6026

    override val message: String = "Collection Not Compatable with Compression, Must be Sized"
}

class MetadataMintMismatch : BubblegumError {
    override val code: Int = 6027

    override val message: String = "Metadata mint does not match collection mint"
}

class InvalidCollectionAuthority : BubblegumError {
    override val code: Int = 6028

    override val message: String = "Invalid collection authority"
}

class InvalidDelegateRecord : BubblegumError {
    override val code: Int = 6029

    override val message: String = "Invalid delegate record pda derivation"
}

class CollectionMasterEditionAccountInvalid : BubblegumError {
    override val code: Int = 6030

    override val message: String = "Edition account doesnt match collection"
}

class CollectionMustBeAUniqueMasterEdition : BubblegumError {
    override val code: Int = 6031

    override val message: String = "Collection Must Be a Unique Master Edition v2"
}

class UnknownExternalError : BubblegumError {
    override val code: Int = 6032

    override val message: String = "Could not convert external error to BubblegumError"
}

class DecompressionDisabled : BubblegumError {
    override val code: Int = 6033

    override val message: String = "Decompression is disabled for this tree."
}
