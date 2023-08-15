package com.metaplex.umi

import com.metaplex.solana_interfaces.Signer
import com.metaplex.solana_interfaces.Transaction
import com.metaplex.solana_interfaces.TransactionBuilder
import com.metaplex.solana_interfaces.TransactionInstruction

class UmiTransactionBuilder : TransactionBuilder {
    private val transaction: Transaction = UmiTransaction()
    override fun addInstruction(transactionInstruction: TransactionInstruction): TransactionBuilder {
        transaction.add(transactionInstruction)
        return this
    }

    override fun setRecentBlockHash(recentBlockHash: String): TransactionBuilder {
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