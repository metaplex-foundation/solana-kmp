package foundation.metaplex.readapi

import com.solana.networking.HttpNetworkDriver
import com.solana.networking.Rpc20Driver
import com.solana.rpccore.JsonRpc20Request
import com.solana.rpccore.get
import foundation.metaplex.solanapublickeys.PublicKey
import kotlinx.serialization.json.Json
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import kotlin.random.Random

@OptIn(ExperimentalObjCName::class)
@ObjCName("ReadApiInterface")
interface ReadApiInterface {
    suspend fun getAsset(assetId: PublicKey): ReadApiAsset
    suspend fun getAssetProof(assetId: PublicKey): GetAssetProofRpcResponse
    suspend fun getAssetsByGroup(input: GetAssetsByGroupRpcInput): ReadApiAssetList
    suspend fun getAssetsByOwner(input: GetAssetsByOwnerRpcInput): ReadApiAssetList
}

@OptIn(ExperimentalObjCName::class)
@ObjCName("ReadApiDecorator")
class ReadApiDecorator(
    private val rpcUrl: String,
    private val httpNetworkDriver: HttpNetworkDriver
): ReadApiInterface {

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    override suspend fun getAsset(assetId: PublicKey): ReadApiAsset {
        val params = json.encodeToJsonElement(GetAsset.serializer(), GetAsset(assetId))
        val rpcRequest = JsonRpc20Request(
            "getAsset",
            id = "${Random.nextInt()}",
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAsset.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetProof(assetId: PublicKey): GetAssetProofRpcResponse {
        val params = json.encodeToJsonElement(GetAsset.serializer(), GetAsset(assetId))
        val rpcRequest = JsonRpc20Request(
            "getAssetProof",
            id = "${Random.nextInt()}",
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, GetAssetProofRpcResponse.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetsByGroup(input: GetAssetsByGroupRpcInput): ReadApiAssetList {
        val params = json.encodeToJsonElement(GetAssetsByGroupRpcInput.serializer(), input)
        val rpcRequest = JsonRpc20Request(
            "getAssetsByGroup",
            id = "${Random.nextInt()}",
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAssetList.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetsByOwner(input: GetAssetsByOwnerRpcInput): ReadApiAssetList {
        val params = json.encodeToJsonElement(GetAssetsByOwnerRpcInput.serializer(), input)
        val rpcRequest = JsonRpc20Request(
            "getAssetsByOwner",
            id = "${Random.nextInt()}",
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAssetList.serializer()).getOrThrow()!!
    }
}