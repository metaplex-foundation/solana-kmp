package foundation.metaplex.solana

import foundation.metaplex.base58.decodeBase58
import foundation.metaplex.solanaeddsa.SolanaEddsa
import foundation.metaplex.solana.MemoProgram.writeUtf8
import foundation.metaplex.solanainterfaces.AccountMeta
import foundation.metaplex.solanainterfaces.Signer
import foundation.metaplex.solanainterfaces.Transaction
import foundation.metaplex.solanainterfaces.TransactionInstruction
import foundation.metaplex.solanakeypair.SolanaKeypair
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.coroutines.test.runTest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MessageTest {

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun transactionBuilderTest() = runTest {
        val memo = "Test memo"
        val transaction: Transaction = SolanaTransactionBuilder()
            .addInstruction(
                writeUtf8(
                    signer().publicKey,
                    memo
                )
            )
            .setRecentBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
            .setSigners(listOf(signer()))
            .build()

        assertEquals(
            "AV6w4Af9PSHhNsTSal4vlPF7Su9QXgCVyfDChHImJITLcS5BlNotKFeMoGw87VwjS3eNA2JCL+MEoReynCNbWAoBAAECBhrZ0FOHFUhTft4+JhhJo9+3/QL6vHWyI8jkatuFPQwFSlNQ+F3IgtYUpVZyeIopbd8eq6vQpgZ4iEky9O72oMviiMGZlPAy5mIJT92z865aQ2ipBrulSCScEzmEJkX1AQEBAAlUZXN0IG1lbW8=",
            Base64.encode(transaction.serialize())
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun transactionBuilderTest2() = runTest {
        val memo = "Other Test memo"
        val transaction: Transaction = SolanaTransactionBuilder()
            .addInstruction(
                writeUtf8(
                    signer().publicKey,
                    memo
                )
            )
            .setRecentBlockHash("Eit7RCyhUixAe2hGBS8oqnw59QK3kgMMjfLME5bm9wRn")
            .setSigners(listOf(signer()))
            .build()
        assertNotEquals(
            "AV6w4Af9PSHhNsTSal4vlPF7Su9QXgCVyfDChHImJITLcS5BlNotKFeMoGw87VwjS3eNA2JCL+MEoReynCNbWAoBAAECBhrZ0FOHFUhTft4+JhhJo9+3/QL6vHWyI8jkatuFPQwFSlNQ+F3IgtYUpVZyeIopbd8eq6vQpgZ4iEky9O72oMviiMGZlPAy5mIJT92z865aQ2ipBrulSCScEzmEJkX1AQEBAAlUZXN0IG1lbW8=",
            Base64.encode(transaction.serialize())
        )
    }

    companion object {

        suspend fun signer(): HotSigner {
            val privateKey = "4Z7cXSyeFR8wNGMVXUE1TwtKn5D5Vu7FzEv69dokLv7KrQk7h6pu4LF8ZRR9yQBhc7uSM6RTTZtU1fmaxiNrxXrs".decodeBase58().copyOfRange(0, 32)
            val k = SolanaEddsa.createKeypairFromSecretKey(privateKey)
            return HotSigner(SolanaKeypair(k.publicKey, k.secretKey))
        }
    }

}

class HotSigner(private val keyPair: SolanaKeypair) : Signer {
    override val publicKey: PublicKey = keyPair.publicKey
    override suspend fun signMessage(message: ByteArray): ByteArray = SolanaEddsa.sign(message, keyPair)

    override suspend fun signTransaction(transaction: Transaction): Transaction {
        transaction.sign(this)
        return transaction
    }

    override suspend fun signAllTransactions(transactions: Array<Transaction>): Array<Transaction> {
        for (transaction in transactions){
            signTransaction(transaction)
        }
        return transactions
    }
}

object MemoProgram {
    private val PROGRAM_ID = PublicKey("Memo1UhkJRfHyvLMcVucJwxXeuD728EqVDDwQDxFMNo")
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
        return TransactionInstruction(PROGRAM_ID, keys, memoBytes)
    }
}