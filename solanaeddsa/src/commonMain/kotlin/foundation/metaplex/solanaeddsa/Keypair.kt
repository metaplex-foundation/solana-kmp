package foundation.metaplex.solanaeddsa

import com.solana.publickey.PublicKey
import com.solana.publickey.SolanaPublicKey
import com.solana.signer.Signer
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * The `Keypair` interface represents a key pair consisting of a public key and a secret key.
 * Implementing classes must provide access to both the public key and the secret key.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("Keypair")
interface Keypair {
    val publicKey: PublicKey
    val secretKey: ByteArray
}

/**
 * The `SolanaKeypair` class represents a concrete implementation of the `Keypair` interface.
 * It provides both the public key and secret key for a Solana key pair.
 *
 * @param publicKey The public key associated with this key pair.
 * @param secretKey The secret key associated with this key pair as a byte array.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("SolanaKeypair")
internal class SolanaKeypair(
    override val publicKey: SolanaPublicKey,
    override val secretKey: ByteArray
) : Keypair


/**
 * The `KeypairSigner` interface extends the `Signer` interface and includes the `Keypair` interface.
 * It represents a key pair that can be used for signing operations.
 */
interface KeypairSigner: Signer, Keypair