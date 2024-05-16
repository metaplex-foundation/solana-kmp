package foundation.metaplex.solana.transactions

import com.metaplex.signer.Signer
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName


/**
 * This interface defines a TransactionBuilder, which is responsible for constructing a transaction.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("TransactionBuilder")
interface TransactionBuilder {

    /**
     * Adds a transaction instruction to the builder.
     *
     * @param transactionInstruction The instruction to add to the transaction.
     * @return This TransactionBuilder instance after adding the instruction.
     */
    fun addInstruction(transactionInstruction: TransactionInstruction): TransactionBuilder

    /**
     * Sets the recent block hash for the transaction.
     *
     * @param recentBlockHash The recent block hash to set for the transaction.
     * @return This TransactionBuilder instance after setting the recent block hash.
     */
    fun setRecentBlockHash(recentBlockHash: Blockhash): TransactionBuilder

    /**
     * Sets the signers for the transaction.
     *
     * @param signers A list of signers for the transaction.
     * @return This TransactionBuilder instance after setting the signers.
     */
    suspend fun setSigners(signers: List<Signer>): TransactionBuilder

    /**
     * Builds the final Transaction object.
     *
     * @return The built Transaction object.
     */
    suspend fun build(): Transaction
}