package com.metaplex.signer

import com.solana.publickey.PublicKey
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

import com.solana.signer.Signer as CoreSigner

/**
 * The Signer interface represents an entity capable of signing messages using a public key.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("Signer")
interface Signer : CoreSigner {
    /**
     * Gets the public key associated with this signer.
     *
     * @return The public key used for signing.
     */
    override val publicKey: PublicKey

    /**
     * Signs the given message.
     *
     * @param message The message to be signed, represented as a byte array.
     * @return The signature of the provided message, also represented as a byte array.
     */
    suspend fun signMessage(message: ByteArray): ByteArray

    //region web3core.Signer
    override val ownerLength: Number
        get() = publicKey.length

    override val signatureLength: Number
        get() = DEFAULT_SIGNATURE_LENGTH

    override suspend fun signPayload(payload: ByteArray): ByteArray {
        return signMessage(payload)
    }
    //endregion

    companion object {
        const val DEFAULT_SIGNATURE_LENGTH = 64
    }
}