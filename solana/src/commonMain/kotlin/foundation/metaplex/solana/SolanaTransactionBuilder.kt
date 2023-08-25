package foundation.metaplex.solana

import foundation.metaplex.solanainterfaces.Blockhash
import foundation.metaplex.solanainterfaces.Signer
import foundation.metaplex.solanainterfaces.Transaction
import foundation.metaplex.solanainterfaces.TransactionBuilder
import foundation.metaplex.solanainterfaces.TransactionInstruction

class SolanaTransactionBuilder : TransactionBuilder {
    private val transaction: Transaction = SolanaTransaction()
    override fun addInstruction(transactionInstruction: TransactionInstruction): TransactionBuilder {
        transaction.add(transactionInstruction)
        return this
    }

    override fun setRecentBlockHash(recentBlockHash: Blockhash): TransactionBuilder {
        transaction.setRecentBlockHash(recentBlockHash)
        return this
    }

    override suspend fun setSigners(signers: List<Signer>): TransactionBuilder {
        transaction.sign(signers)
        return this
    }

    override suspend fun build(): Transaction {
        return transaction
    }
}