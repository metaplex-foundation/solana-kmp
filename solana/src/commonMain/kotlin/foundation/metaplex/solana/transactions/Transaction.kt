package foundation.metaplex.solana.transactions

import com.solana.publickey.PublicKey
import com.solana.signer.Signer
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * A Uint8Array that represents a transaction signature.
 * @category Transactions
 */
typealias TransactionSignature = ByteArray

/**
 * A Uint8Array that represents the serialized message of a transaction.
 * @category Transactions
 */
typealias SerializedTransactionMessage = ByteArray

/**
 * A Uint8Array that represents a serialized transaction.
 * @category Transactions
 */
typealias SerializedTransaction = ByteArray


/**
 * Defines a transaction error.
 * @category Transactions
 */
typealias TransactionError = String;

/**
 * The maximum amount of bytes that can be used for a transaction.
 * @category Transactions
 */
val TRANSACTION_SIZE_LIMIT = 1232;

val DEFAULT_SIGNATURE = TransactionSignature(0)

/**
 * Defines an account required by an instruction.
 * It includes its public key, whether it is signing the
 * transaction and whether the account should be writable.
 *
 * @category Transactions
 */
data class AccountMeta(
    var publicKey: PublicKey,
    var isSigner: Boolean,
    var isWritable: Boolean
) {
    override fun toString(): String {
        return "pubkey:${publicKey.string()}, signer:$isSigner, writable:$isWritable"
    }
}

class SerializeConfig(
    val requireAllSignatures: Boolean = true,
    val verifySignatures: Boolean = true
)

data class TransactionInstruction(
    var programId: PublicKey,
    var keys: List<AccountMeta>,
    var data: ByteArray = DEFAULT_SIGNATURE
)

data class SignaturePubkeyPair(
    var signature: TransactionSignature?,
    val publicKey: PublicKey
)

class NonceInformation(
    val nonce: String,
    val nonceInstruction: TransactionInstruction
)

/**
 * Defines a blockhash.
 * @category Transactions
 */
typealias Blockhash = String

/**
 * Represents a transaction that can be constructed, signed, and executed within a certain context.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("Transaction")
interface Transaction {
    /**
     * Adds one or more transaction instructions to the transaction.
     *
     * @param instruction One or more [TransactionInstruction] objects representing the instructions to add.
     * @return The updated transaction object.
     */
    fun addInstruction(vararg instruction: TransactionInstruction): Transaction

    /**
     * A shorthand method to add one or more transaction instructions to the transaction.
     *
     * @param instruction One or more [TransactionInstruction] objects representing the instructions to add.
     * @return The updated transaction object.
     */
    fun add(vararg instruction: TransactionInstruction): Transaction

    /**
     * Sets the recent block hash for the transaction.
     *
     * @param recentBlockhash The recent block hash to set for the transaction.
     */
    fun setRecentBlockHash(recentBlockhash: String)

    /**
     * Signs the transaction using the provided signers.
     *
     * @param signer One or more [Signer] objects representing the entities signing the transaction.
     */
    suspend fun sign(vararg signer: Signer)

    /**
     * Signs the transaction using the provided list of signers.
     *
     * @param signers A list of [Signer] objects representing the entities signing the transaction.
     */
    suspend fun sign(signers: List<Signer>)

    /**
     * Partially signs the transaction using the provided signers.
     *
     * @param signers One or more [Signer] objects representing the entities partially signing the transaction.
     */
    suspend fun partialSign(vararg signers: Signer)

    /**
     * Adds a signature to the transaction.
     *
     * @param pubkey The public key associated with the signature.
     * @param signature The signature as a byte array.
     */
    fun addSignature(pubkey: PublicKey, signature: TransactionSignature)

    /**
     * Verifies the signatures present on the transaction.
     *
     * @return `true` if all signatures are valid, otherwise `false`.
     */
    suspend fun verifySignatures(): Boolean

    /**
     * Compiles the transaction into a [Message] object containing instructions and signatures.
     *
     * @return A [Message] object representing the compiled transaction data.
     */
    fun compileMessage(): Message

    /**
     * Retrieves a buffer of the transaction data that need to be covered by signatures.
     *
     * @return The transaction data buffer as a byte array.
     */
    fun serializeMessage(): SerializedTransactionMessage

    /**
     * Serializes the transaction into the wire format.
     *
     * @param config Serialization configuration options (optional).
     * @return The serialized transaction as a byte array.
     */
    suspend fun serialize(config: SerializeConfig = SerializeConfig()): SerializedTransaction

    /**
     * Serializes the transaction with additional sign data.
     *
     * @param signData The additional sign data to include in serialization.
     * @return The serialized transaction as a byte array.
     */
    suspend fun serialize(signData: ByteArray): SerializedTransactionMessage
}