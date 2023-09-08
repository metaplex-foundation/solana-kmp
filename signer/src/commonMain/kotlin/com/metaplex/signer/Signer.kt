package com.metaplex.signer

import foundation.metaplex.solanapublickeys.PublicKey

/**
 * The Signer interface represents an entity capable of signing messages using a public key.
 */
interface Signer {
    /**
     * Gets the public key associated with this signer.
     *
     * @return The public key used for signing.
     */
    val publicKey: PublicKey;

    /**
     * Signs the given message.
     *
     * @param message The message to be signed, represented as a byte array.
     * @return The signature of the provided message, also represented as a byte array.
     */
    suspend fun signMessage(message: ByteArray): ByteArray
}