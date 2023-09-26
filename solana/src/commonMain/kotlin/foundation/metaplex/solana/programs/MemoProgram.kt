package foundation.metaplex.solana.programs

import foundation.metaplex.solana.transactions.AccountMeta
import foundation.metaplex.solana.transactions.TransactionInstruction
import foundation.metaplex.solanapublickeys.PublicKey

/**
 * Interface for the Memo program, used for writing UTF-8 data into Solana transactions.
 */
object MemoProgram : Program() {
    val PROGRAM_ID = PublicKey("Memo1UhkJRfHyvLMcVucJwxXeuD728EqVDDwQDxFMNo")

    /**
     * Returns a [TransactionInstruction] object containing instructions to call the Memo program with the
     * specified memo.
     * @param account signer pubkey
     * @param memo utf-8 string to be written into Solana transaction
     * @return [TransactionInstruction] object with memo instruction
     */
    fun writeUtf8(account: PublicKey, memo: String): TransactionInstruction {
        // Add signer to AccountMeta keys
        val keys = listOf(
            AccountMeta(
                account,
                true,
                false
            )
        )

        // Convert memo string to UTF-8 byte array
        val memoBytes = memo.encodeToByteArray()
        return createTransactionInstruction(
            PROGRAM_ID,
            keys,
            memoBytes
        )
    }
}