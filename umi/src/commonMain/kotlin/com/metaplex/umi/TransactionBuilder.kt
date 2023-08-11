package com.metaplex.umi

class TransactionBuilder {
    private val transaction: Transaction = Transaction()
    fun addInstruction(transactionInstruction: TransactionInstruction): TransactionBuilder {
        transaction.add(transactionInstruction)
        return this
    }

    fun setRecentBlockHash(recentBlockHash: String): TransactionBuilder {
        transaction.setRecentBlockHash(recentBlockHash)
        return this
    }

    suspend fun setSigners(signers: List<Signer>): TransactionBuilder {
        transaction.sign(signers)
        return this
    }

    suspend fun build(): Transaction {
        return transaction
    }
}