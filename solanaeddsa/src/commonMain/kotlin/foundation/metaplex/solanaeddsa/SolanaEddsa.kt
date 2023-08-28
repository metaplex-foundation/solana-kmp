package foundation.metaplex.solanaeddsa

import foundation.metaplex.solanakeypair.SolanaKeypair
import foundation.metaplex.solanainterfaces.EddsaInterface
import foundation.metaplex.solanainterfaces.Keypair
import foundation.metaplex.solanapublickeys.Pda
import foundation.metaplex.solanapublickeys.PublicKey
import diglol.crypto.Ed25519

class SolanaEddsa {
    companion object: EddsaInterface {
        override suspend fun generateKeypair(): Keypair {
            val keypair = Ed25519.generateKeyPair()
            return SolanaKeypair(PublicKey(keypair.publicKey), keypair.privateKey)
        }

        override suspend fun createKeypairFromSecretKey(secretKey: ByteArray): Keypair {
            val keypair = Ed25519.generateKeyPair(secretKey)
            return SolanaKeypair(PublicKey(keypair.publicKey), keypair.privateKey)
        }

        override suspend fun createKeypairFromSeed(seed: ByteArray): Keypair {
            TODO("Not yet implemented")
        }

        override suspend fun isOnCurve(publicKey: PublicKey): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun findPda(
            programId: PublicKey,
            seeds: Array<ByteArray>
        ): Pda = PublicKey.findProgramAddress(seeds.toList(), programId)

        override suspend fun sign(message: ByteArray, keypair: Keypair): ByteArray = Ed25519.sign(keypair.secretKey, message)

        override suspend fun verify(
            message: ByteArray,
            signature: ByteArray,
            publicKey: PublicKey
        ): Boolean = Ed25519.verify(signature, publicKey.toByteArray(), message)
    }
}