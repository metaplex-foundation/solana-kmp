package foundation.metaplex.solana.programs

import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.jvm.JvmStatic


/**
 * Abstract class for
 */
abstract class Program {
    companion object {
        /**
         * Returns a [TransactionInstruction] built from the specified values.
         * @param programId Solana program we are calling
         * @param keys AccountMeta keys
         * @param data byte array sent to Solana
         * @return [TransactionInstruction] object containing specified values
         */
        @JvmStatic
        fun createTransactionInstruction(
            programId: PublicKey,
            keys: List<AccountMeta>,
            data: ByteArray
        ): TransactionInstruction {
            return TransactionInstruction(programId, keys, data)
        }
    }
}