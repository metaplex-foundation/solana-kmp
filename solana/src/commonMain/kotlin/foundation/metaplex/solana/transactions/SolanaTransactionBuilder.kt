package foundation.metaplex.solana.transactions

import com.metaplex.signer.Signer
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("SolanaTransactionBuilder")
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