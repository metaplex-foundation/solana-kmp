package foundation.metaplex.solana.transactions

import com.solana.publickey.PublicKey
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

data class CompiledInstruction(
    val programIdIndex: Int,
    val accounts: List<Int>,
    val data: String
)

data class MessageHeader (
    var numRequiredSignatures: Byte = 0,
    var numReadonlySignedAccounts: Byte = 0,
    var numReadonlyUnsignedAccounts: Byte = 0
) {
    fun toByteArray(): ByteArray {
        return byteArrayOf(
            numRequiredSignatures,
            numReadonlySignedAccounts,
            numReadonlyUnsignedAccounts
        )
    }

    companion object {
        const val HEADER_LENGTH = 3
    }
}

/**
 * Represents a message to be sent over a network, containing instructions and associated data.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("Message")
interface Message {

    /**
     * Gets the header of the message.
     */
    val header: MessageHeader

    /**
     * Gets the list of account public keys associated with this message.
     */
    val accountKeys: List<PublicKey>

    /**
     * Gets the recent blockhash used for this message.
     */
    val recentBlockhash: String

    /**
     * Gets the list of compiled instructions included in this message.
     */
    val instructions: List<CompiledInstruction>

    /**
     * Checks whether the account at the specified index is a signer for this message.
     *
     * @param index The index of the account to check.
     * @return True if the account at the specified index is a signer, otherwise false.
     */
    fun isAccountSigner(index: Int): Boolean

    /**
     * Checks whether the account at the specified index is writable for this message.
     *
     * @param index The index of the account to check.
     * @return True if the account at the specified index is writable, otherwise false.
     */
    fun isAccountWritable(index: Int): Boolean

    /**
     * Checks whether the account at the specified index represents a program ID for this message.
     *
     * @param index The index of the account to check.
     * @return True if the account at the specified index represents a program ID, otherwise false.
     */
    fun isProgramId(index: Int): Boolean

    /**
     * Gets the list of program public keys associated with this message.
     *
     * @return The list of program public keys.
     */
    fun programIds(): List<PublicKey>

    /**
     * Gets the list of non-program public keys associated with this message.
     *
     * @return The list of non-program public keys.
     */
    fun nonProgramIds(): List<PublicKey>

    /**
     * Serializes the message into a byte array for network transmission.
     *
     * @return The serialized byte array representing the message.
     */
    fun serialize(): ByteArray

    /**
     * Sets the fee payer's public key for this message.
     *
     * @param publicKey The public key of the fee payer.
     */
    fun setFeePayer(publicKey: PublicKey)
}
