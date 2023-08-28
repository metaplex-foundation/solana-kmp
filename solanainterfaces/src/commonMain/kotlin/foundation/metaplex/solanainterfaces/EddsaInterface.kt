package foundation.metaplex.solanainterfaces

import foundation.metaplex.solanapublickeys.Pda
import foundation.metaplex.solanapublickeys.PublicKey

interface EddsaInterface {
    /** Generates a new keypair. */
    suspend fun generateKeypair(): Keypair
    /** Restores a keypair from a secret key. */
    suspend fun createKeypairFromSecretKey(secretKey: ByteArray): Keypair
    /** Restores a keypair from a seed. */
    suspend fun createKeypairFromSeed(seed: ByteArray): Keypair
    /** Whether the given public key is on the EdDSA elliptic curve. */
    suspend fun isOnCurve(publicKey: PublicKey): Boolean
    /** Finds a Program-Derived Address from the given programId and seeds. */
    suspend fun findPda(programId: PublicKey, seeds: Array<ByteArray>): Pda
    /** Signs a message with the given keypair. */
    suspend fun sign(message: ByteArray, keypair: Keypair): ByteArray
    /** Verifies a signature for a message with the given public key. */
    suspend fun verify(message: ByteArray, signature: ByteArray, publicKey: PublicKey): Boolean
}