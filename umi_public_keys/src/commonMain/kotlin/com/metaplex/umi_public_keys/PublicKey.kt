package com.metaplex.umi_public_keys

import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.metaplex.base58.decodeBase58
import com.metaplex.base58.encodeToBase58String
import diglol.crypto.Ed25519
import diglol.crypto.Hash

const val PUBLIC_KEY_LENGTH = 32

/**
 * The amount of bytes in a public key.
 * @category Signers and PublicKeys
 */
data class PublicKey(val pubkey: ByteArray) {
    init {
        require(pubkey.size <= PUBLIC_KEY_LENGTH) { "Invalid public key input" }
    }

    constructor(pubkeyString: String) : this(pubkeyString.decodeBase58())

    fun toByteArray(): ByteArray = pubkey

    fun toBase58(): String = pubkey.encodeToBase58String()

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

        suspend fun createProgramAddress(seeds: List<ByteArray>, programId: PublicKey): PublicKey {
            val seedSize = seeds.sumOf { it.count() }
            val bufferSize = seedSize + programId.toByteArray().count() + "ProgramDerivedAddress".encodeToByteArray().count()
            val buffer = PlatformBuffer.allocate(bufferSize)
            for (seed in seeds) {
                require(seed.size <= 32) { "Max seed length exceeded" }
                try {
                    buffer.writeBytes(seed)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
            try {
                buffer.writeBytes(programId.toByteArray())
                buffer.writeBytes("ProgramDerivedAddress".encodeToByteArray())
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            buffer.resetForRead()
            val hash = Hash(type = Hash.Type.SHA256).hash(buffer.readByteArray(bufferSize))
            /*if (TweetNaclFast.is_on_curve(hash) != 0) {
                throw RuntimeException("Invalid seeds, address must fall off the curve")
            }*/
            return PublicKey(hash)
        }

        @Throws(Exception::class)
        suspend fun findProgramAddress(
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

