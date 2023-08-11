package com.metaplex.umi

import com.metaplex.base58.decodeBase58
import com.metaplex.umi.MemoProgram.writeUtf8
import com.metaplex.umi_public_keys.PublicKey
import diglol.crypto.Ed25519
import diglol.crypto.KeyPair
import kotlinx.coroutines.runBlocking
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MessageTest {

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun transactionBuilderTest() = runBlocking {
        val memo = "Test memo"
        val transaction: Transaction = TransactionBuilder()
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
    fun transactionBuilderTest2() = runBlocking {
        val memo = "Other Test memo"
        val transaction: Transaction = TransactionBuilder()
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
            return HotSigner(Ed25519.generateKeyPair(privateKey))
        }
    }

}

class HotSigner(private val keyPair: KeyPair) : Signer {
    override val publicKey: PublicKey = PublicKey(keyPair.publicKey)
    override suspend fun signMessage(message: ByteArray): ByteArray {
        return Ed25519.sign(keyPair, message)
    }

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