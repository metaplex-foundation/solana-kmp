package foundation.metaplex.solana

import foundation.metaplex.solana_interfaces.Blockhash
import foundation.metaplex.solana_interfaces.Signer
import foundation.metaplex.solana_interfaces.Transaction
import foundation.metaplex.solana_interfaces.TransactionBuilder
import foundation.metaplex.solana_interfaces.TransactionInstruction

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