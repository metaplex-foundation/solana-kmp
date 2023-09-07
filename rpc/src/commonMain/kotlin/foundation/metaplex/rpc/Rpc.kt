package foundation.metaplex.rpc

import com.funkatronics.networking.HttpNetworkDriver
import com.funkatronics.networking.Rpc20Driver
import com.funkatronics.rpccore.JsonRpc20Request
import com.funkatronics.rpccore.get
import foundation.metaplex.rpc.networking.NetworkDriver
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.random.Random
import kotlin.random.nextUInt

/**
 * Represents an RPC client for making remote procedure calls to a specified RPC server.
 *
 * @param rpcUrl The URL of the RPC server to which RPC requests will be sent.
 * @param httpNetworkDriver An optional HTTP network driver used for making HTTP requests.
 *                         Defaults to a standard NetworkDriver if not provided.
 */
class RPC(
    private val rpcUrl: String,
    private val httpNetworkDriver: HttpNetworkDriver = NetworkDriver(),
): RpcInterface {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        explicitNulls = false
    }

    /**
     * Override the 'getAccountInfo' function from the RpcInterface interface to fetch account information.
     *
     * @param publicKey The public key of the account for which information is requested.
     * @param configuration Optional configuration for the RPC request, or null.
     * @param serializer The serializer used for deserializing the response into an Account object.
     * @return An [Account] object representing the account information, or null if not found.
     *
     * Example:
     * ```
     * val accountInfo = getAccountInfo(
     *     randomPublicKey,
     *     null,
     *     serializer = BorshAsBase64JsonArraySerializer(Metadata.serializer())
     * )
     * ```
     */
    override suspend fun <T>getAccountInfo(
        publicKey: PublicKey,
        configuration: RpcGetAccountInfoConfigurationRpcInput?,
        serializer: KSerializer<T>,
    ): Account<T>? {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(publicKey.toBase58()))

        // Use the provided configuration or create a default one
        val fixedConfiguration = configuration ?: RpcGetAccountInfoConfigurationRpcInput()
        params.add(json.encodeToJsonElement(RpcGetAccountInfoConfigurationRpcInput.serializer(), fixedConfiguration))

        // Create an RPC request object with a unique ID
        val rpcRequest = JsonRpc20Request(
            "getAccountInfo",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        return rpcDriver.get(rpcRequest, AccountInfoSerializer(serializer)).getOrThrow()
    }
}