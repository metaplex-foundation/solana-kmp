//
// Errors
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-07-18
//
package foundation.metaplex.mpltokenmetadata.generated

import kotlin.Int
import kotlin.String

sealed interface TokenMetadataError {
    val code: Int

    val message: String
}

class InstructionUnpackError : TokenMetadataError {
    override val code: Int = 0

    override val message: String = ""
}

class InstructionPackError : TokenMetadataError {
    override val code: Int = 1

    override val message: String = ""
}

class NotRentExempt : TokenMetadataError {
    override val code: Int = 2

    override val message: String = "Lamport balance below rent-exempt threshold"
}

class AlreadyInitialized : TokenMetadataError {
    override val code: Int = 3

    override val message: String = "Already initialized"
}

class Uninitialized : TokenMetadataError {
    override val code: Int = 4

    override val message: String = "Uninitialized"
}

class InvalidMetadataKey : TokenMetadataError {
    override val code: Int = 5

    override val message: String =
            " Metadata's key must match seed of ['metadata', program id, mint] provided"
}

class InvalidEditionKey : TokenMetadataError {
    override val code: Int = 6

    override val message: String =
            "Edition's key must match seed of ['metadata', program id, name, 'edition'] provided"
}

class UpdateAuthorityIncorrect : TokenMetadataError {
    override val code: Int = 7

    override val message: String = "Update Authority given does not match"
}

class UpdateAuthorityIsNotSigner : TokenMetadataError {
    override val code: Int = 8

    override val message: String = "Update Authority needs to be signer to update metadata"
}

class NotMintAuthority : TokenMetadataError {
    override val code: Int = 9

    override val message: String = "You must be the mint authority and signer on this transaction"
}

class InvalidMintAuthority : TokenMetadataError {
    override val code: Int = 10

    override val message: String =
            "Mint authority provided does not match the authority on the mint"
}

class NameTooLong : TokenMetadataError {
    override val code: Int = 11

    override val message: String = "Name too long"
}

class SymbolTooLong : TokenMetadataError {
    override val code: Int = 12

    override val message: String = "Symbol too long"
}

class UriTooLong : TokenMetadataError {
    override val code: Int = 13

    override val message: String = "URI too long"
}

class UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner : TokenMetadataError {
    override val code: Int = 14

    override val message: String = ""
}

class MintMismatch : TokenMetadataError {
    override val code: Int = 15

    override val message: String = "Mint given does not match mint on Metadata"
}

class EditionsMustHaveExactlyOneToken : TokenMetadataError {
    override val code: Int = 16

    override val message: String = "Editions must have exactly one token"
}

class MaxEditionsMintedAlready : TokenMetadataError {
    override val code: Int = 17

    override val message: String = ""
}

class TokenMintToFailed : TokenMetadataError {
    override val code: Int = 18

    override val message: String = ""
}

class MasterRecordMismatch : TokenMetadataError {
    override val code: Int = 19

    override val message: String = ""
}

class DestinationMintMismatch : TokenMetadataError {
    override val code: Int = 20

    override val message: String = ""
}

class EditionAlreadyMinted : TokenMetadataError {
    override val code: Int = 21

    override val message: String = ""
}

class PrintingMintDecimalsShouldBeZero : TokenMetadataError {
    override val code: Int = 22

    override val message: String = ""
}

class OneTimePrintingAuthorizationMintDecimalsShouldBeZero : TokenMetadataError {
    override val code: Int = 23

    override val message: String = ""
}

class EditionMintDecimalsShouldBeZero : TokenMetadataError {
    override val code: Int = 24

    override val message: String = "EditionMintDecimalsShouldBeZero"
}

class TokenBurnFailed : TokenMetadataError {
    override val code: Int = 25

    override val message: String = ""
}

class TokenAccountOneTimeAuthMintMismatch : TokenMetadataError {
    override val code: Int = 26

    override val message: String = ""
}

class DerivedKeyInvalid : TokenMetadataError {
    override val code: Int = 27

    override val message: String = "Derived key invalid"
}

class PrintingMintMismatch : TokenMetadataError {
    override val code: Int = 28

    override val message: String = "The Printing mint does not match that on the master edition!"
}

class OneTimePrintingAuthMintMismatch : TokenMetadataError {
    override val code: Int = 29

    override val message: String =
            "The One Time Printing Auth mint does not match that on the master edition!"
}

class TokenAccountMintMismatch : TokenMetadataError {
    override val code: Int = 30

    override val message: String = "The mint of the token account does not match the Printing mint!"
}

class TokenAccountMintMismatchV2 : TokenMetadataError {
    override val code: Int = 31

    override val message: String =
            "The mint of the token account does not match the master metadata mint!"
}

class NotEnoughTokens : TokenMetadataError {
    override val code: Int = 32

    override val message: String = "Not enough tokens to mint a limited edition"
}

class PrintingMintAuthorizationAccountMismatch : TokenMetadataError {
    override val code: Int = 33

    override val message: String = ""
}

class AuthorizationTokenAccountOwnerMismatch : TokenMetadataError {
    override val code: Int = 34

    override val message: String = ""
}

class Disabled : TokenMetadataError {
    override val code: Int = 35

    override val message: String = ""
}

class CreatorsTooLong : TokenMetadataError {
    override val code: Int = 36

    override val message: String = "Creators list too long"
}

class CreatorsMustBeAtleastOne : TokenMetadataError {
    override val code: Int = 37

    override val message: String = "Creators must be at least one if set"
}

class MustBeOneOfCreators : TokenMetadataError {
    override val code: Int = 38

    override val message: String = ""
}

class NoCreatorsPresentOnMetadata : TokenMetadataError {
    override val code: Int = 39

    override val message: String = "This metadata does not have creators"
}

class CreatorNotFound : TokenMetadataError {
    override val code: Int = 40

    override val message: String = "This creator address was not found"
}

class InvalidBasisPoints : TokenMetadataError {
    override val code: Int = 41

    override val message: String = "Basis points cannot be more than 10000"
}

class PrimarySaleCanOnlyBeFlippedToTrue : TokenMetadataError {
    override val code: Int = 42

    override val message: String = "Primary sale can only be flipped to true and is immutable"
}

class OwnerMismatch : TokenMetadataError {
    override val code: Int = 43

    override val message: String = "Owner does not match that on the account given"
}

class NoBalanceInAccountForAuthorization : TokenMetadataError {
    override val code: Int = 44

    override val message: String = "This account has no tokens to be used for authorization"
}

class ShareTotalMustBe100 : TokenMetadataError {
    override val code: Int = 45

    override val message: String = "Share total must equal 100 for creator array"
}

class ReservationExists : TokenMetadataError {
    override val code: Int = 46

    override val message: String = ""
}

class ReservationDoesNotExist : TokenMetadataError {
    override val code: Int = 47

    override val message: String = ""
}

class ReservationNotSet : TokenMetadataError {
    override val code: Int = 48

    override val message: String = ""
}

class ReservationAlreadyMade : TokenMetadataError {
    override val code: Int = 49

    override val message: String = ""
}

class BeyondMaxAddressSize : TokenMetadataError {
    override val code: Int = 50

    override val message: String = ""
}

class NumericalOverflowError : TokenMetadataError {
    override val code: Int = 51

    override val message: String = "NumericalOverflowError"
}

class ReservationBreachesMaximumSupply : TokenMetadataError {
    override val code: Int = 52

    override val message: String = ""
}

class AddressNotInReservation : TokenMetadataError {
    override val code: Int = 53

    override val message: String = ""
}

class CannotVerifyAnotherCreator : TokenMetadataError {
    override val code: Int = 54

    override val message: String = "You cannot unilaterally verify another creator, they must sign"
}

class CannotUnverifyAnotherCreator : TokenMetadataError {
    override val code: Int = 55

    override val message: String = "You cannot unilaterally unverify another creator"
}

class SpotMismatch : TokenMetadataError {
    override val code: Int = 56

    override val message: String = ""
}

class IncorrectOwner : TokenMetadataError {
    override val code: Int = 57

    override val message: String = "Incorrect account owner"
}

class PrintingWouldBreachMaximumSupply : TokenMetadataError {
    override val code: Int = 58

    override val message: String = ""
}

class DataIsImmutable : TokenMetadataError {
    override val code: Int = 59

    override val message: String = "Data is immutable"
}

class DuplicateCreatorAddress : TokenMetadataError {
    override val code: Int = 60

    override val message: String = "No duplicate creator addresses"
}

class ReservationSpotsRemainingShouldMatchTotalSpotsAtStart : TokenMetadataError {
    override val code: Int = 61

    override val message: String = ""
}

class InvalidTokenProgram : TokenMetadataError {
    override val code: Int = 62

    override val message: String = "Invalid token program"
}

class DataTypeMismatch : TokenMetadataError {
    override val code: Int = 63

    override val message: String = "Data type mismatch"
}

class BeyondAlottedAddressSize : TokenMetadataError {
    override val code: Int = 64

    override val message: String = ""
}

class ReservationNotComplete : TokenMetadataError {
    override val code: Int = 65

    override val message: String = ""
}

class TriedToReplaceAnExistingReservation : TokenMetadataError {
    override val code: Int = 66

    override val message: String = ""
}

class InvalidOperation : TokenMetadataError {
    override val code: Int = 67

    override val message: String = "Invalid operation"
}

class InvalidOwner : TokenMetadataError {
    override val code: Int = 68

    override val message: String = "Invalid Owner"
}

class PrintingMintSupplyMustBeZeroForConversion : TokenMetadataError {
    override val code: Int = 69

    override val message: String = "Printing mint supply must be zero for conversion"
}

class OneTimeAuthMintSupplyMustBeZeroForConversion : TokenMetadataError {
    override val code: Int = 70

    override val message: String = "One Time Auth mint supply must be zero for conversion"
}

class InvalidEditionIndex : TokenMetadataError {
    override val code: Int = 71

    override val message: String =
            "You tried to insert one edition too many into an edition mark pda"
}

class ReservationArrayShouldBeSizeOne : TokenMetadataError {
    override val code: Int = 72

    override val message: String = ""
}

class IsMutableCanOnlyBeFlippedToFalse : TokenMetadataError {
    override val code: Int = 73

    override val message: String = "Is Mutable can only be flipped to false"
}

class CollectionCannotBeVerifiedInThisInstruction : TokenMetadataError {
    override val code: Int = 74

    override val message: String = "Collection cannot be verified in this instruction"
}

class Removed : TokenMetadataError {
    override val code: Int = 75

    override val message: String =
            "This instruction was deprecated in a previous release and is now removed"
}

class MustBeBurned : TokenMetadataError {
    override val code: Int = 76

    override val message: String = ""
}

class InvalidUseMethod : TokenMetadataError {
    override val code: Int = 77

    override val message: String = "This use method is invalid"
}

class CannotChangeUseMethodAfterFirstUse : TokenMetadataError {
    override val code: Int = 78

    override val message: String = "Cannot Change Use Method after the first use"
}

class CannotChangeUsesAfterFirstUse : TokenMetadataError {
    override val code: Int = 79

    override val message: String = "Cannot Change Remaining or Available uses after the first use"
}

class CollectionNotFound : TokenMetadataError {
    override val code: Int = 80

    override val message: String = "Collection Not Found on Metadata"
}

class InvalidCollectionUpdateAuthority : TokenMetadataError {
    override val code: Int = 81

    override val message: String = "Collection Update Authority is invalid"
}

class CollectionMustBeAUniqueMasterEdition : TokenMetadataError {
    override val code: Int = 82

    override val message: String = "Collection Must Be a Unique Master Edition v2"
}

class UseAuthorityRecordAlreadyExists : TokenMetadataError {
    override val code: Int = 83

    override val message: String =
            "The Use Authority Record Already Exists, to modify it Revoke, then Approve"
}

class UseAuthorityRecordAlreadyRevoked : TokenMetadataError {
    override val code: Int = 84

    override val message: String = "The Use Authority Record is empty or already revoked"
}

class Unusable : TokenMetadataError {
    override val code: Int = 85

    override val message: String = "This token has no uses"
}

class NotEnoughUses : TokenMetadataError {
    override val code: Int = 86

    override val message: String = "There are not enough Uses left on this token."
}

class CollectionAuthorityRecordAlreadyExists : TokenMetadataError {
    override val code: Int = 87

    override val message: String = "This Collection Authority Record Already Exists."
}

class CollectionAuthorityDoesNotExist : TokenMetadataError {
    override val code: Int = 88

    override val message: String = "This Collection Authority Record Does Not Exist."
}

class InvalidUseAuthorityRecord : TokenMetadataError {
    override val code: Int = 89

    override val message: String = "This Use Authority Record is invalid."
}

class InvalidCollectionAuthorityRecord : TokenMetadataError {
    override val code: Int = 90

    override val message: String = ""
}

class InvalidFreezeAuthority : TokenMetadataError {
    override val code: Int = 91

    override val message: String = "Metadata does not match the freeze authority on the mint"
}

class InvalidDelegate : TokenMetadataError {
    override val code: Int = 92

    override val message: String =
            "All tokens in this account have not been delegated to this user."
}

class CannotAdjustVerifiedCreator : TokenMetadataError {
    override val code: Int = 93

    override val message: String = ""
}

class CannotRemoveVerifiedCreator : TokenMetadataError {
    override val code: Int = 94

    override val message: String = "Verified creators cannot be removed."
}

class CannotWipeVerifiedCreators : TokenMetadataError {
    override val code: Int = 95

    override val message: String = ""
}

class NotAllowedToChangeSellerFeeBasisPoints : TokenMetadataError {
    override val code: Int = 96

    override val message: String = ""
}

class EditionOverrideCannotBeZero : TokenMetadataError {
    override val code: Int = 97

    override val message: String = "Edition override cannot be zero"
}

class InvalidUser : TokenMetadataError {
    override val code: Int = 98

    override val message: String = "Invalid User"
}

class RevokeCollectionAuthoritySignerIncorrect : TokenMetadataError {
    override val code: Int = 99

    override val message: String = "Revoke Collection Authority signer is incorrect"
}

class TokenCloseFailed : TokenMetadataError {
    override val code: Int = 100

    override val message: String = ""
}

class UnsizedCollection : TokenMetadataError {
    override val code: Int = 101

    override val message: String = "Can't use this function on unsized collection"
}

class SizedCollection : TokenMetadataError {
    override val code: Int = 102

    override val message: String = "Can't use this function on a sized collection"
}

class MissingCollectionMetadata : TokenMetadataError {
    override val code: Int = 103

    override val message: String = "Missing collection metadata account"
}

class NotAMemberOfCollection : TokenMetadataError {
    override val code: Int = 104

    override val message: String = "This NFT is not a member of the specified collection."
}

class NotVerifiedMemberOfCollection : TokenMetadataError {
    override val code: Int = 105

    override val message: String = "This NFT is not a verified member of the specified collection."
}

class NotACollectionParent : TokenMetadataError {
    override val code: Int = 106

    override val message: String = "This NFT is not a collection parent NFT."
}

class CouldNotDetermineTokenStandard : TokenMetadataError {
    override val code: Int = 107

    override val message: String = "Could not determine a TokenStandard type."
}

class MissingEditionAccount : TokenMetadataError {
    override val code: Int = 108

    override val message: String = "This mint account has an edition but none was provided."
}

class NotAMasterEdition : TokenMetadataError {
    override val code: Int = 109

    override val message: String = "This edition is not a Master Edition"
}

class MasterEditionHasPrints : TokenMetadataError {
    override val code: Int = 110

    override val message: String = "This Master Edition has existing prints"
}

class BorshDeserializationError : TokenMetadataError {
    override val code: Int = 111

    override val message: String = ""
}

class CannotUpdateVerifiedCollection : TokenMetadataError {
    override val code: Int = 112

    override val message: String = "Cannot update a verified collection in this command"
}

class CollectionMasterEditionAccountInvalid : TokenMetadataError {
    override val code: Int = 113

    override val message: String = "Edition account doesnt match collection "
}

class AlreadyVerified : TokenMetadataError {
    override val code: Int = 114

    override val message: String = "Item is already verified."
}

class AlreadyUnverified : TokenMetadataError {
    override val code: Int = 115

    override val message: String = ""
}

class NotAPrintEdition : TokenMetadataError {
    override val code: Int = 116

    override val message: String = "This edition is not a Print Edition"
}

class InvalidMasterEdition : TokenMetadataError {
    override val code: Int = 117

    override val message: String = "Invalid Master Edition"
}

class InvalidPrintEdition : TokenMetadataError {
    override val code: Int = 118

    override val message: String = "Invalid Print Edition"
}

class InvalidEditionMarker : TokenMetadataError {
    override val code: Int = 119

    override val message: String = "Invalid Edition Marker"
}

class ReservationListDeprecated : TokenMetadataError {
    override val code: Int = 120

    override val message: String = "Reservation List is Deprecated"
}

class PrintEditionDoesNotMatchMasterEdition : TokenMetadataError {
    override val code: Int = 121

    override val message: String = "Print Edition does not match Master Edition"
}

class EditionNumberGreaterThanMaxSupply : TokenMetadataError {
    override val code: Int = 122

    override val message: String = "Edition Number greater than max supply"
}

class MustUnverify : TokenMetadataError {
    override val code: Int = 123

    override val message: String = "Must unverify before migrating collections."
}

class InvalidEscrowBumpSeed : TokenMetadataError {
    override val code: Int = 124

    override val message: String = "Invalid Escrow Account Bump Seed"
}

class MustBeEscrowAuthority : TokenMetadataError {
    override val code: Int = 125

    override val message: String = "Must Escrow Authority"
}

class InvalidSystemProgram : TokenMetadataError {
    override val code: Int = 126

    override val message: String = "Invalid System Program"
}

class MustBeNonFungible : TokenMetadataError {
    override val code: Int = 127

    override val message: String = "Must be a Non Fungible Token"
}

class InsufficientTokens : TokenMetadataError {
    override val code: Int = 128

    override val message: String = "Insufficient tokens for transfer"
}

class BorshSerializationError : TokenMetadataError {
    override val code: Int = 129

    override val message: String = "Borsh Serialization Error"
}

class NoFreezeAuthoritySet : TokenMetadataError {
    override val code: Int = 130

    override val message: String = "Cannot create NFT with no Freeze Authority."
}

class InvalidCollectionSizeChange : TokenMetadataError {
    override val code: Int = 131

    override val message: String = "Invalid collection size change"
}

class InvalidBubblegumSigner : TokenMetadataError {
    override val code: Int = 132

    override val message: String = "Invalid bubblegum signer"
}

class EscrowParentHasDelegate : TokenMetadataError {
    override val code: Int = 133

    override val message: String = "Escrow parent cannot have a delegate"
}

class MintIsNotSigner : TokenMetadataError {
    override val code: Int = 134

    override val message: String = "Mint needs to be signer to initialize the account"
}

class InvalidTokenStandard : TokenMetadataError {
    override val code: Int = 135

    override val message: String = "Invalid token standard"
}

class InvalidMintForTokenStandard : TokenMetadataError {
    override val code: Int = 136

    override val message: String = "Invalid mint account for specified token standard"
}

class InvalidAuthorizationRules : TokenMetadataError {
    override val code: Int = 137

    override val message: String = "Invalid authorization rules account"
}

class MissingAuthorizationRules : TokenMetadataError {
    override val code: Int = 138

    override val message: String = "Missing authorization rules account"
}

class MissingProgrammableConfig : TokenMetadataError {
    override val code: Int = 139

    override val message: String = "Missing programmable configuration"
}

class InvalidProgrammableConfig : TokenMetadataError {
    override val code: Int = 140

    override val message: String = "Invalid programmable configuration"
}

class DelegateAlreadyExists : TokenMetadataError {
    override val code: Int = 141

    override val message: String = "Delegate already exists"
}

class DelegateNotFound : TokenMetadataError {
    override val code: Int = 142

    override val message: String = "Delegate not found"
}

class MissingAccountInBuilder : TokenMetadataError {
    override val code: Int = 143

    override val message: String = "Required account not set in instruction builder"
}

class MissingArgumentInBuilder : TokenMetadataError {
    override val code: Int = 144

    override val message: String = "Required argument not set in instruction builder"
}

class FeatureNotSupported : TokenMetadataError {
    override val code: Int = 145

    override val message: String = "Feature not supported currently"
}

class InvalidSystemWallet : TokenMetadataError {
    override val code: Int = 146

    override val message: String = "Invalid system wallet"
}

class OnlySaleDelegateCanTransfer : TokenMetadataError {
    override val code: Int = 147

    override val message: String = "Only the sale delegate can transfer while its set"
}

class MissingTokenAccount : TokenMetadataError {
    override val code: Int = 148

    override val message: String = "Missing token account"
}

class MissingSplTokenProgram : TokenMetadataError {
    override val code: Int = 149

    override val message: String = "Missing SPL token program"
}

class MissingAuthorizationRulesProgram : TokenMetadataError {
    override val code: Int = 150

    override val message: String = "Missing authorization rules program"
}

class InvalidDelegateRoleForTransfer : TokenMetadataError {
    override val code: Int = 151

    override val message: String = "Invalid delegate role for transfer"
}

class InvalidTransferAuthority : TokenMetadataError {
    override val code: Int = 152

    override val message: String = "Invalid transfer authority"
}

class InstructionNotSupported : TokenMetadataError {
    override val code: Int = 153

    override val message: String = "Instruction not supported for ProgrammableNonFungible assets"
}

class KeyMismatch : TokenMetadataError {
    override val code: Int = 154

    override val message: String = "Public key does not match expected value"
}

class LockedToken : TokenMetadataError {
    override val code: Int = 155

    override val message: String = "Token is locked"
}

class UnlockedToken : TokenMetadataError {
    override val code: Int = 156

    override val message: String = "Token is unlocked"
}

class MissingDelegateRole : TokenMetadataError {
    override val code: Int = 157

    override val message: String = "Missing delegate role"
}

class InvalidAuthorityType : TokenMetadataError {
    override val code: Int = 158

    override val message: String = "Invalid authority type"
}

class MissingTokenRecord : TokenMetadataError {
    override val code: Int = 159

    override val message: String = "Missing token record account"
}

class MintSupplyMustBeZero : TokenMetadataError {
    override val code: Int = 160

    override val message: String = "Mint supply must be zero for programmable assets"
}

class DataIsEmptyOrZeroed : TokenMetadataError {
    override val code: Int = 161

    override val message: String = "Data is empty or zeroed"
}

class MissingTokenOwnerAccount : TokenMetadataError {
    override val code: Int = 162

    override val message: String = "Missing token owner"
}

class InvalidMasterEditionAccountLength : TokenMetadataError {
    override val code: Int = 163

    override val message: String = "Master edition account has an invalid length"
}

class IncorrectTokenState : TokenMetadataError {
    override val code: Int = 164

    override val message: String = "Incorrect token state"
}

class InvalidDelegateRole : TokenMetadataError {
    override val code: Int = 165

    override val message: String = "Invalid delegate role"
}

class MissingPrintSupply : TokenMetadataError {
    override val code: Int = 166

    override val message: String = "Print supply is required for non-fungibles"
}

class MissingMasterEditionAccount : TokenMetadataError {
    override val code: Int = 167

    override val message: String = "Missing master edition account"
}

class AmountMustBeGreaterThanZero : TokenMetadataError {
    override val code: Int = 168

    override val message: String = "Amount must be greater than zero"
}

class InvalidDelegateArgs : TokenMetadataError {
    override val code: Int = 169

    override val message: String = "Invalid delegate args"
}

class MissingLockedTransferAddress : TokenMetadataError {
    override val code: Int = 170

    override val message: String = "Missing address for locked transfer"
}

class InvalidLockedTransferAddress : TokenMetadataError {
    override val code: Int = 171

    override val message: String = "Invalid destination address for locked transfer"
}

class DataIncrementLimitExceeded : TokenMetadataError {
    override val code: Int = 172

    override val message: String = "Exceeded account realloc increase limit"
}

class CannotUpdateAssetWithDelegate : TokenMetadataError {
    override val code: Int = 173

    override val message: String =
            "Cannot update the rule set of a programmable asset that has a delegate"
}

class InvalidAmount : TokenMetadataError {
    override val code: Int = 174

    override val message: String = "Invalid token amount for this operation or token standard"
}

class MissingMasterEditionMintAccount : TokenMetadataError {
    override val code: Int = 175

    override val message: String = "Missing master edition mint account"
}

class MissingMasterEditionTokenAccount : TokenMetadataError {
    override val code: Int = 176

    override val message: String = "Missing master edition token account"
}

class MissingEditionMarkerAccount : TokenMetadataError {
    override val code: Int = 177

    override val message: String = "Missing edition marker account"
}

class CannotBurnWithDelegate : TokenMetadataError {
    override val code: Int = 178

    override val message: String = "Cannot burn while persistent delegate is set"
}

class MissingEdition : TokenMetadataError {
    override val code: Int = 179

    override val message: String = "Missing edition account"
}

class InvalidAssociatedTokenAccountProgram : TokenMetadataError {
    override val code: Int = 180

    override val message: String = "Invalid Associated Token Account Program"
}

class InvalidInstructionsSysvar : TokenMetadataError {
    override val code: Int = 181

    override val message: String = "Invalid InstructionsSysvar"
}

class InvalidParentAccounts : TokenMetadataError {
    override val code: Int = 182

    override val message: String = "Invalid or Unneeded parent accounts"
}

class InvalidUpdateArgs : TokenMetadataError {
    override val code: Int = 183

    override val message: String = "Authority cannot apply all update args"
}

class InsufficientTokenBalance : TokenMetadataError {
    override val code: Int = 184

    override val message: String = "Token account does not have enough tokens"
}

class MissingCollectionMint : TokenMetadataError {
    override val code: Int = 185

    override val message: String = "Missing collection account"
}

class MissingCollectionMasterEdition : TokenMetadataError {
    override val code: Int = 186

    override val message: String = "Missing collection master edition account"
}

class InvalidTokenRecord : TokenMetadataError {
    override val code: Int = 187

    override val message: String = "Invalid token record account"
}

class InvalidCloseAuthority : TokenMetadataError {
    override val code: Int = 188

    override val message: String = "The close authority needs to be revoked by the Utility Delegate"
}

class InvalidInstruction : TokenMetadataError {
    override val code: Int = 189

    override val message: String = "Invalid or removed instruction"
}

class MissingDelegateRecord : TokenMetadataError {
    override val code: Int = 190

    override val message: String = "Missing delegate record"
}

class InvalidFeeAccount : TokenMetadataError {
    override val code: Int = 191

    override val message: String = ""
}

class InvalidMetadataFlags : TokenMetadataError {
    override val code: Int = 192

    override val message: String = ""
}
