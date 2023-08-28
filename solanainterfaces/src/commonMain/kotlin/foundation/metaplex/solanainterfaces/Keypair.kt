package foundation.metaplex.solanainterfaces

import foundation.metaplex.solanapublickeys.PublicKey

/**
 * Represents a keypair with a public key and a secret key.
 * @category Signers and PublicKeys
 */
interface Keypair {
    val publicKey: PublicKey
    val secretKey: ByteArray
}

/**
 * Represent a {@link Signer} that can is aware of its secret key.
 * @category Signers and PublicKeys
 */
interface KeypairSigner: Signer, Keypair