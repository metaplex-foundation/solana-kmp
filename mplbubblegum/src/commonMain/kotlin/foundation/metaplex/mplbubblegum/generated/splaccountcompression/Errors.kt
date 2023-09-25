//
// Errors
// Metaplex
//
// This code was generated locally by Funkatronics on 2023-09-11
//
package foundation.metaplex.mplbubblegum.generated.splaccountcompression

import kotlin.Int
import kotlin.String

sealed interface SPLAccountCompressionError {
    val code: Int

    val message: String
}

class IncorrectLeafLength : SPLAccountCompressionError {
    override val code: Int = 6000

    override val message: String = "Incorrect leaf length. Expected vec of 32 bytes"
}

class ConcurrentMerkleTreeError : SPLAccountCompressionError {
    override val code: Int = 6001

    override val message: String = "Concurrent merkle tree error"
}

class ZeroCopyError : SPLAccountCompressionError {
    override val code: Int = 6002

    override val message: String = "Issue zero copying concurrent merkle tree data"
}

class ConcurrentMerkleTreeConstantsError : SPLAccountCompressionError {
    override val code: Int = 6003

    override val message: String =
            "An unsupported max depth or max buffer size constant was provided"
}

class CanopyLengthMismatch : SPLAccountCompressionError {
    override val code: Int = 6004

    override val message: String = "Expected a different byte length for the merkle tree canopy"
}

class IncorrectAuthority : SPLAccountCompressionError {
    override val code: Int = 6005

    override val message: String = "Provided authority does not match expected tree authority"
}

class IncorrectAccountOwner : SPLAccountCompressionError {
    override val code: Int = 6006

    override val message: String =
            "Account is owned by a different program, expected it to be owned by this program"
}

class IncorrectAccountType : SPLAccountCompressionError {
    override val code: Int = 6007

    override val message: String = "Account provided has incorrect account type"
}

class LeafIndexOutOfBounds : SPLAccountCompressionError {
    override val code: Int = 6008

    override val message: String = "Leaf index of concurrent merkle tree is out of bounds"
}
