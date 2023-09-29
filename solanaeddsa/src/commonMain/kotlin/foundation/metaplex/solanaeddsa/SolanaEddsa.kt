package foundation.metaplex.solanaeddsa

import diglol.crypto.Ed25519
import foundation.metaplex.solanapublickeys.Pda
import foundation.metaplex.solanapublickeys.PublicKey
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName


/**
 * The `SolanaEddsa` object provides utility functions for working with Ed25519 cryptographic operations
 * within the Solana blockchain ecosystem.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("SolanaEddsa")
object SolanaEddsa {
    /**
     * Generates a new Ed25519 key pair.
     *
     * @return A new Ed25519 key pair represented by a [Keypair] object.
     */
    suspend fun generateKeypair(): Keypair {
        val keypair = Ed25519.generateKeyPair()
        return SolanaKeypair(PublicKey(keypair.publicKey), keypair.privateKey)
    }

    /**
     * Creates an Ed25519 key pair from a given secret key.
     *
     * @param secretKey The secret key as a byte array.
     * @return An Ed25519 key pair represented by a [Keypair] object.
     */
    suspend fun createKeypairFromSecretKey(secretKey: ByteArray): Keypair {
        val keypair = Ed25519.generateKeyPair(secretKey)
        return SolanaKeypair(PublicKey(keypair.publicKey), keypair.privateKey)
    }

    /**
     * Creates an Ed25519 key pair from a seed (not yet implemented).
     *
     * @param seed The seed as a byte array.
     * @return An Ed25519 key pair represented by a [Keypair] object.
     */
    suspend fun createKeypairFromSeed(seed: ByteArray): Keypair {
        TODO("Not yet implemented")
    }

    suspend fun isOnCurve(publicKey: PublicKey): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Checks if a given public key is on the Ed25519 curve (not yet implemented).
     *
     * @param publicKey The public key to check.
     * @return `true` if the public key is on the curve; `false` otherwise.
     */
    suspend fun findPda(
        programId: PublicKey,
        seeds: Array<ByteArray>
    ): Pda = PublicKey.findProgramAddress(seeds.toList(), programId)


    /**
     * Finds the program-derived address (PDA) for a given program ID and seed values.
     *
     * @param programId The program's public key.
     * @param seeds An array of seed values as byte arrays.
     * @return The program-derived address (PDA) as a [Pda] object.
     */
    suspend fun sign(message: ByteArray, keypair: Keypair): ByteArray =
        Ed25519.sign(keypair.secretKey, message)

    /**
     * Signs a message using the provided key pair's secret key.
     *
     * @param message The message to sign as a byte array.
     * @param keypair The key pair used for signing.
     * @return The signature of the message as a byte array.
     */
    suspend fun verify(
        message: ByteArray,
        signature: ByteArray,
        publicKey: PublicKey
    ): Boolean = Ed25519.verify(signature, publicKey.toByteArray(), message)
}