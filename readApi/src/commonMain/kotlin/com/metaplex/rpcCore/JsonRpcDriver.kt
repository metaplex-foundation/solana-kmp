package com.metaplex.rpcCore

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement

interface JsonRpcDriver {
    suspend fun <R> makeRequest(request: RpcRequest, resultSerializer: KSerializer<R>): RpcResponse<R>
}

suspend inline fun <reified R> JsonRpcDriver.get(
    request: RpcRequest,
    serializer: KSerializer<R>
): Result<R?> =
    this.makeRequest(request, serializer).let { response ->
        (response.result)?.let { result ->
            return Result.success(result)
        }

        response.error?.let {
            return Result.failure(Error(it.message))
        }

        // an empty error and empty result means we did not find anything, return null
        return Result.success(null)
    }

suspend fun JsonRpcDriver.makeRequest(request: RpcRequest): DefaultRpcResponse =
    makeRequest(request, JsonElement.serializer())