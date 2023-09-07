package foundation.metaplex.rpc

import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 * Represents an interface for making remote procedure calls (RPC).
 */
interface RpcInterface {

    /**
     * Retrieves account information for a given public key.
     *
     * @param publicKey The public key of the account.
     * @param configuration Optional configuration for the RPC request. Defaults to Base64 Encoding
     * @param serializer The serializer used for deserializing the response.
     * @return An [Account] object representing the account information, or null if not found.
     */
    suspend fun <T> getAccountInfo(
        publicKey: PublicKey,
        configuration: RpcGetAccountInfoConfigurationRpcInput?,
        serializer: KSerializer<T>,
    ): Account<T>?
}

/**
 * Configuration input for the [RpcInterface.getAccountInfo] function.
 */
@Serializable
data class RpcGetAccountInfoConfigurationRpcInput(
    val encoding: Encoding = Encoding.base64,
    val commitment: Commitment? = null,
    val minContextSlot: UInt? = null,
    val dataSlice: RpcDataSlice? = null
)

/**
 * Enumeration representing the commitment level for RPC requests.
 */
@Serializable
enum class Commitment {
    processed,
    confirmed,
    finalized
}

/**
 * Represents a data slice used in RPC configuration.
 */
@Serializable
data class RpcDataSlice(val offset: UInt, val length: Unit)

/**
 * Sealed class representing data filters for RPC requests.
 */
@Serializable
sealed class RpcDataFilter

/**
 * Represents a data filter based on data size.
 */
@Serializable
data class RpcDataFilterSize(val dataSize: UInt) : RpcDataFilter()

/**
 * Represents a memory comparison operation used as a data filter.
 */
@Serializable
class Memcmp(val offset: UInt, val bytes: ByteArray)

/**
 * Represents a data filter based on memory comparison.
 */
@Serializable
data class RpcDataFilterMemcmp(val memcmp: Memcmp) : RpcDataFilter()

/**
 * Enumeration representing different encoding formats for RPC requests.
 */
@Serializable
enum class Encoding {
    base64
}