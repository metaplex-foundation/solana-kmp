package com.metaplex.readapi

import com.metaplex.networking.HttpNetworkDriver
import com.metaplex.networking.Rpc20Driver
import com.metaplex.rpcCore.JsonRpc20Request
import com.metaplex.rpcCore.get
import com.metaplex.serialization.PublicKeyAsStringSerializer
import com.metaplex.solana_public_keys.PublicKey
import it.lamba.random.uuid
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.random.Random


interface ReadApiInterface {
    suspend fun getAsset(assetId: PublicKey): ReadApiAsset
    suspend fun getAssetProof(assetId: PublicKey): GetAssetProofRpcResponse
    suspend fun getAssetsByGroup(input: GetAssetsByGroupRpcInput): ReadApiAssetList
    suspend fun getAssetsByOwner(input: GetAssetsByOwnerRpcInput): ReadApiAssetList
}

class ReadApiDecorator(
    private val rpcUrl: String = "https://api.invalid.solana.com",
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
            id = Random.uuid(),
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAsset.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetProof(assetId: PublicKey): GetAssetProofRpcResponse {
        val params = json.encodeToJsonElement(GetAsset.serializer(), GetAsset(assetId))
        val rpcRequest = JsonRpc20Request(
            "getAssetProof",
            id = Random.uuid(),
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, GetAssetProofRpcResponse.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetsByGroup(input: GetAssetsByGroupRpcInput): ReadApiAssetList {
        val params = json.encodeToJsonElement(GetAssetsByGroupRpcInput.serializer(), input)
        val rpcRequest = JsonRpc20Request(
            "getAssetsByGroup",
            id = Random.uuid(),
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAssetList.serializer()).getOrThrow()!!
    }

    override suspend fun getAssetsByOwner(input: GetAssetsByOwnerRpcInput): ReadApiAssetList {
        val params = json.encodeToJsonElement(GetAssetsByOwnerRpcInput.serializer(), input)
        val rpcRequest = JsonRpc20Request(
            "getAssetsByOwner",
            id = Random.uuid(),
            params = params
        )
        val rpcDriver = Rpc20Driver(rpcUrl, httpNetworkDriver)
        return rpcDriver.get(rpcRequest, ReadApiAssetList.serializer()).getOrThrow()!!
    }
}