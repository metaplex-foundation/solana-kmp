package foundation.metaplex.solanapublickeys

import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.solana.publickey.SolanaPublicKey
import foundation.metaplex.base58.decodeBase58
import diglol.crypto.Hash
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

const val PUBLIC_KEY_LENGTH = SolanaPublicKey.PUBLIC_KEY_LENGTH

/**
 * Defines a Program-Derived Address.
 *
 * It is a public key with the bump number that was used
 * to ensure the address is not on the ed25519 curve.
 *
 * @category Signers and PublicKeys
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("Pda")
data class Pda(val address: PublicKey, val nonce: Int)


/**
 * Defines an object that has a public key.
 * @category Signers and PublicKeys
 */
interface HasPublicKey {
    val publicKey: PublicKey
}

/**
 * The amount of bytes in a public key.
 * @category Signers and PublicKeys
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("PublicKey")
data class PublicKey(val publicKeyBytes: ByteArray) : SolanaPublicKey(publicKeyBytes) {
    init {
        require(publicKeyBytes.size <= PUBLIC_KEY_LENGTH) { "Invalid public key input" }
    }

    constructor(base58String: String) : this(base58String.decodeBase58())

    fun toByteArray(): ByteArray = publicKeyBytes

    fun toBase58(): String = base58()

    fun equals(pubkey: PublicKey): Boolean = this.publicKeyBytes.contentEquals(pubkey.toByteArray())

    override fun string(): String = toBase58()

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + publicKeyBytes.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val person = other as PublicKey
        return equals(person)
    }

    override fun toString(): String = toBase58()


    companion object {
        const val PUBLIC_KEY_LENGTH = SolanaPublicKey.PUBLIC_KEY_LENGTH

        fun readPubkey(bytes: ByteArray, offset: Int): PublicKey {
            val buf = bytes.copyOfRange(offset, offset + PUBLIC_KEY_LENGTH)
            return PublicKey(buf)
        }

        suspend fun createProgramAddress(seeds: List<ByteArray>, programId: SolanaPublicKey): PublicKey {
            val seedSize = seeds.sumOf { it.count() }
            val bufferSize = seedSize + programId.bytes.count() + "ProgramDerivedAddress".encodeToByteArray().count()
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
                buffer.writeBytes(programId.bytes)
                buffer.writeBytes("ProgramDerivedAddress".encodeToByteArray())
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            buffer.resetForRead()
            val hash = Hash(type = Hash.Type.SHA256).hash(buffer.readByteArray(bufferSize))
            if (TweetNaclFast.is_on_curve(hash) != 0) {
                throw RuntimeException("Invalid seeds, address must fall off the curve")
            }
            return PublicKey(hash)
        }

        @Throws(Exception::class)
        suspend fun findProgramAddress(
            seeds: List<ByteArray>,
            programId: SolanaPublicKey
        ): Pda {
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
                return Pda(address, nonce)
            }
            throw Exception("Unable to find a viable program address nonce")
        }

        fun valueOf(publicKey: String): PublicKey {
            return PublicKey(publicKey)
        }
    }
}

/**
 * Creates the default public key which is composed of all zero bytes.
 * @category Signers and PublicKeys
 */
fun defaultPublicKey() = PublicKey("11111111111111111111111111111111")