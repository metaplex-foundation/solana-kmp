package com.metaplex.umi_public_keys

import fr.acinq.bitcoin.Base58
import fr.acinq.bitcoin.Crypto.isPubKeyValid
import fr.acinq.bitcoin.io.ByteArrayOutput
import fr.acinq.bitcoin.Crypto.sha256

const val PUBLIC_KEY_LENGTH = 32;

/**
 * The amount of bytes in a public key.
 * @category Signers and PublicKeys
 */
data class PublicKey(val pubkey: ByteArray) {
    init {
        require(pubkey.size <= PUBLIC_KEY_LENGTH) { "Invalid public key input" }
    }

    constructor(pubkeyString: String) : this(Base58.decode(pubkeyString))

    fun toByteArray(): ByteArray = pubkey

    fun toBase58(): String = Base58.encode(pubkey)

    fun equals(pubkey: PublicKey): Boolean = this.pubkey.contentEquals(pubkey.toByteArray())

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + pubkey.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val person = other as PublicKey
        return equals(person)
    }

    override fun toString(): String = toBase58()

    class ProgramDerivedAddress(val address: PublicKey, val nonce: Int)
    companion object {
        const val PUBLIC_KEY_LENGTH = 32

        fun readPubkey(bytes: ByteArray, offset: Int): PublicKey {
            val buf = bytes.copyOfRange(offset, offset + PUBLIC_KEY_LENGTH)
            return PublicKey(buf)
        }

        fun createProgramAddress(seeds: List<ByteArray>, programId: PublicKey): PublicKey {
            val buffer = ByteArrayOutput()
            for (seed in seeds) {
                require(seed.size <= 32) { "Max seed length exceeded" }
                try {
                    buffer.write(seed)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
            try {
                buffer.write(programId.toByteArray())
                buffer.write("ProgramDerivedAddress".encodeToByteArray())
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            val hash = sha256(buffer.toByteArray())

            /*if (!isPubKeyValid(hash)) {
                throw RuntimeException("Invalid seeds, address must fall off the curve")
            }*/
            return PublicKey(hash)
        }

        @Throws(Exception::class)
        fun findProgramAddress(
            seeds: List<ByteArray>,
            programId: PublicKey
        ): ProgramDerivedAddress {
            var nonce = 255
            val address: PublicKey
            val seedsWithNonce: MutableList<ByteArray> = ArrayList()
            seedsWithNonce.addAll(seeds)
            while (nonce != 0) {
                address = try {
                    seedsWithNonce.add(byteArrayOf(nonce.toByte()))
                    createProgramAddress(seedsWithNonce, programId)
                } catch (e: Exception) {
                    seedsWithNonce.removeAt(seedsWithNonce.size - 1)
                    nonce--
                    continue
                }
                return ProgramDerivedAddress(address, nonce)
            }
            throw Exception("Unable to find a viable program address nonce")
        }

        fun valueOf(publicKey: String): PublicKey {
            return PublicKey(publicKey)
        }
    }
}

