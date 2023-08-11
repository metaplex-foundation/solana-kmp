package com.metaplex.umi

import com.metaplex.umi_public_keys.PublicKey

interface Signer {
    val publicKey: PublicKey;
    /** Signs the given message. */
    suspend fun signMessage(message: ByteArray): ByteArray
    /** Signs the given transaction. */
    suspend fun signTransaction(transaction: Transaction): Transaction
    /** Signs all the given transactions at once. */
    suspend fun signAllTransactions( transactions: Array<Transaction>): Array<Transaction>
}