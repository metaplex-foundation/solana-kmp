package foundation.metaplex.rpc

import com.solana.networking.HttpNetworkDriver
import com.solana.networking.Rpc20Driver
import com.solana.rpccore.JsonRpc20Request
import com.solana.rpccore.get
import foundation.metaplex.rpc.networking.NetworkDriver
import foundation.metaplex.rpc.serializers.SolanaResponseSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.experimental.ExperimentalObjCName
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.native.ObjCName
import kotlin.random.Random
import kotlin.random.nextUInt

import com.solana.publickey.SolanaPublicKey as PublicKey

/**
 * Represents an RPC client for making remote procedure calls to a specified RPC server.
 *
 * @param rpcUrl The URL of the RPC server to which RPC requests will be sent.
 * @param httpNetworkDriver An optional HTTP network driver used for making HTTP requests.
 *                         Defaults to a standard NetworkDriver if not provided.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("RPC")
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
    override suspend fun <T> getAccountInfo(
        publicKey: PublicKey,
        configuration: RpcGetAccountInfoConfiguration?,
        serializer: KSerializer<T>,
    ): Account<T>? {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(publicKey.base58()))

        // Use the provided configuration or create a default one
        val fixedConfiguration = configuration ?: RpcGetAccountInfoConfiguration()
        params.add(json.encodeToJsonElement(RpcGetAccountInfoConfiguration.serializer(), fixedConfiguration))

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

    /**
     * Retrieves the account information for multiple public keys.
     *
     * This function allows you to query the account information for multiple accounts
     * in a single request, potentially optimizing network use and reducing latency compared
     * to making separate requests for each account.
     *
     * @param publicKeys A list of public keys of the accounts for which information is requested.
     * @param configuration Optional configuration for the RPC request. This allows the caller
     *                      to specify details such as the encoding format to be used and
     *                      the commitment level to apply when retrieving the data.
     *                      Defaults to {@link RpcGetMultipleAccountsConfiguration} with base64 encoding
     *                      and default commitment and context slot values if not provided.
     * @param serializer The serializer to be used for deserializing the response into a list
     *                   of [Account] objects.
     * @return A list of [Account] objects representing the account information for each
     *         public key in the `publicKeys` parameter. Each element in the list corresponds
     *         to a public key in the `publicKeys` parameter, and can be null if the account
     *         information for a given public key could not be found.
     *
     * Example usage:
     * ```
     * val accountInfos = rpcInterface.getMultipleAccounts(
     *     listOf(publicKey1, publicKey2),
     *     RpcGetMultipleAccountsConfiguration(encoding = Encoding.jsonParsed),
     *     Account.serializer()
     * )
     * ```
     */
    override suspend fun <T> getMultipleAccounts(
        publicKeys: List<PublicKey>,
        configuration: RpcGetMultipleAccountsConfiguration?,
        serializer: KSerializer<T>
    ): List<Account<T>?>? {
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(publicKeys.map { it.base58() }))

        val fixedConfiguration = configuration ?: RpcGetMultipleAccountsConfiguration()
        params.add(json.encodeToJsonElement(RpcGetMultipleAccountsConfiguration.serializer(), fixedConfiguration))

        // Create an RPC request object with a unique ID
        val rpcRequest = JsonRpc20Request(
            "getMultipleAccounts",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        return rpcDriver.get(rpcRequest,
            SolanaResponseSerializer(
                ListSerializer(
                    Account.serializer(
                        serializer
                    )
                )
            )
        ).getOrThrow()
    }

    /**
     * Retrieves a list of all accounts associated with the specified program ID from the Solana blockchain.
     *
     * This method allows for fetching all accounts associated with a particular program ID, which might include
     * various types of information such as account balances, associated data, etc. This method can be particularly
     * useful in scenarios where you need to retrieve a snapshot of all accounts linked to a specific program at a given
     * point in time.
     *
     * @param programId The public key of the program ID for which to fetch the associated accounts.
     * @param configuration Optional configuration for the RPC request, allowing to set additional parameters for
     *                      the request such as filters. If not provided, a default configuration is used.
     * @param serializer The Kotlin Serialization serializer to be used to deserialize the response into a list of
     *                   [Account] objects, allowing to work with the response in a type-safe manner.
     * @return A list of [Account] objects representing the information of each account associated with the specified
     *         program ID. Each element in the list represents an account, and can be null if the information for a
     *         particular account could not be retrieved.
     *
     * @throws Exception If there is an error during the RPC request or during the deserialization of the response.
     *
     * Example usage:
     * ```
     * val programAccounts = rpc.getProgramAccounts(
     *     programId,
     *     RpcGetProgramAccountsConfiguration(filters = listOf(...)),
     *     MyAccountSerializer()
     * )
     * ```
     */
    override suspend fun <T> getProgramAccounts(
        programId: PublicKey,
        configuration: RpcGetProgramAccountsConfiguration?,
        serializer: KSerializer<T>
    ): List<Account<T>?>? {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(programId.base58()))

        // Use the provided configuration or create a default one
        val fixedConfiguration = configuration ?: RpcGetProgramAccountsConfiguration()
        params.add(json.encodeToJsonElement(RpcGetProgramAccountsConfiguration.serializer(), fixedConfiguration))

        // Create an RPC request object with a unique ID
        val rpcRequest = JsonRpc20Request(
            "getProgramAccounts",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        return rpcDriver.get(rpcRequest,
            SolanaResponseSerializer(
                ListSerializer(
                    Account.serializer(
                        serializer
                    )
                )
            )
        ).getOrThrow()
    }


    /**
     * Sends a serialized transaction to the Solana blockchain.
     *
     * @param transaction The serialized transaction to be sent.
     * @param configuration An optional configuration to customize how the transaction is sent.
     *                     Defaults to an instance of {@link RpcSendTransactionConfiguration} with default values
     *                     if not provided.
     * @return The signature of the successfully submitted transaction.
     *
     * @throws Exception If there is any error during the RPC request or the deserialization of the response.
     *
     * Example usage:
     * ```
     * val signature = sendTransaction(
     *     serializedTransaction,
     *     RpcSendTransactionConfiguration(skipPreflight = true)
     * )
     * ```
     */
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun sendTransaction(
        transaction: SerializedTransaction,
        configuration: RpcSendTransactionConfiguration?
    ): TransactionSignature {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(Base64.encode(transaction)))

        // Use the provided configuration or create a default one
        val fixedConfiguration = configuration ?: RpcSendTransactionConfiguration()
        params.add(json.encodeToJsonElement(RpcSendTransactionConfiguration.serializer(), fixedConfiguration))

        val rpcRequest = JsonRpc20Request(
            "sendTransaction",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        val response = rpcDriver.get(
            rpcRequest, String.serializer()
        ).getOrThrow()!!
        return response.encodeToByteArray()
    }

    /**
     * Retrieves the latest blockhash from the Solana blockchain, along with the associated expiry block height.
     *
     * @param configuration An optional configuration to customize the RPC request.
     *                     Defaults to null, indicating the default configuration will be used.
     * @return An object containing the latest blockhash and its associated expiry block height.
     *
     * @throws Exception If there is any error during the RPC request or the deserialization of the response.
     *
     * Example usage:
     * ```
     * val blockInfo = getLatestBlockhash(
     *     RpcGetLatestBlockhashConfiguration(commitment = Commitment.Finalized)
     * )
     * ```
     */
    override suspend fun getLatestBlockhash(
        configuration: RpcGetLatestBlockhashConfiguration?
    ): BlockhashWithExpiryBlockHeight {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()

        // Use the provided configuration or create a default one
        configuration?.let {
            params.add(json.encodeToJsonElement(RpcGetLatestBlockhashConfiguration.serializer(), it))
        }
        val rpcRequest = JsonRpc20Request(
            "getLatestBlockhash",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        return rpcDriver.get(
            rpcRequest, SolanaResponseSerializer(BlockhashWithExpiryBlockHeight.serializer())
        ).getOrThrow()!!
    }

    /**
     * Retrieves the current slot from the Solana blockchain.
     *
     * @param configuration An optional configuration to customize the RPC request.
     *                     Defaults to null, indicating the default configuration will be used.
     * @return An object containing the current slot
     *
     * @throws Exception If there is any error during the RPC request or the deserialization of the response.
     *
     * Example usage:
     * ```
     * val slotInfo = getSlot(
     *     RpcGetSlotConfiguration(commitment = Commitment.Finalized)
     * )
     * ```
     */
    override suspend fun getSlot(
        configuration: RpcGetSlotConfiguration?): ULong {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        // Use the provided configuration or create a default one
        configuration?.let {
            params.add(json.encodeToJsonElement(RpcGetSlotConfiguration.serializer(), it))
        }
        val rpcRequest = JsonRpc20Request(
            "getSlot",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        return rpcDriver.get(
            rpcRequest, ULong.serializer())
        .getOrThrow()!!
    }

    /**
     * Retrieves the minimum balance required to make account rent exempt from the Solana blockchain.
     *
     * @param usize the Account's data length
     * @return An object containing the minimum balance required to make account rent exempt.
     *
     * @throws Exception If there is any error during the RPC request or the deserialization of the response.
     *
     * Example usage:
     * ```
     * val usizeInfo = getMinimumBalanceForRentExemption(
     *     usize(commitment = Commitment.Finalized)
     * )
     * ```
     */
    override suspend fun getMinimumBalanceForRentExemption(usize: ULong): ULong {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(usize))
        val rpcRequest = JsonRpc20Request(
            "getMinimumBalanceForRentExemption",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        return rpcDriver.get(
            rpcRequest, ULong.serializer())
            .getOrThrow()!!
    }

    /**
     * Retrieves the balance of the account of provided Pubkey from the Solana blockchain.
     *
     * @param Pubkey of account to query, as base-58 encoded string
     * @return The balance of the account of provided Pubkey
     *
     * @throws Exception If there is any error during the RPC request or the deserialization of the response.
     *
     * Example usage:
     * ```
     * val Pubkey = getBalance(
     *     Pubkey(commitment = Commitment.Finalized)
     * )
     * ```
     */
    override suspend fun getBalance(
        publicKey: PublicKey,
        configuration: RpcGetBalanceConfiguration?): Long {
        // Create a list to hold JSON elements for RPC request parameters
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(publicKey.base58()))
        // Use the provided configuration or create a default one
        configuration?.let {
            params.add(json.encodeToJsonElement(RpcGetBalanceConfiguration.serializer(), it))
        }
        val rpcRequest = JsonRpc20Request(
            "getBalance",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )

        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(
            rpcRequest, SolanaResponseSerializer(Long.serializer()))
            .getOrThrow()!!
    }

    /**
     * Requests an airdrop of tokens to the specified account on the Solana blockchain.
     *
     * @param configuration The configuration for the airdrop request.
     * @return A transaction signature representing the result of the airdrop request.
     */
    override suspend fun requestAirdrop(configuration: RpcRequestAirdropConfiguration): TransactionSignature {
        val params: MutableList<JsonElement> = mutableListOf()
        params.add(json.encodeToJsonElement(configuration.publicKey.base58()))
        params.add(json.encodeToJsonElement(configuration.lamports.basisPoints.longValue()))
        configuration.commitment?.let {
            params.add(json.encodeToJsonElement(configuration.commitment.name))
        }
        val rpcRequest = JsonRpc20Request(
            "requestAirdrop",
            id = "${Random.nextUInt()}",
            params = JsonArray(content = params)
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)

        // Execute the RPC request and deserialize the response using the provided serializer
        val response = rpcDriver.get(
            rpcRequest, String.serializer())
            .getOrThrow()!!
        return response.encodeToByteArray()
    }
}