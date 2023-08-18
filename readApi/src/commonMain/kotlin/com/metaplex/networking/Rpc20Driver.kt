package com.metaplex.networking

import com.metaplex.rpcCore.JsonRpcDriver
import com.metaplex.rpcCore.Rpc20Response
import com.metaplex.rpcCore.RpcError
import com.metaplex.rpcCore.RpcRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class Rpc20Driver(private val url: String,
                  private val httpDriver: HttpNetworkDriver) : JsonRpcDriver {

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    override suspend fun <R> makeRequest(request: RpcRequest, resultSerializer: KSerializer<R>): Rpc20Response<R> =
        httpDriver.makeHttpRequest(
            HttpPostRequest(
                url = url,
                properties = mapOf("Content-Type" to "application/json; charset=utf-8"),
                body = json.encodeToString(RpcRequest.serializer(), request)
            )
        ).run {
            try {
                json.decodeFromString(Rpc20Response.serializer(resultSerializer), this)
            } catch (e: Exception) {
                Rpc20Response(error = RpcError(-1, e.message ?: this))
            }
        }

    internal class HttpPostRequest(
        override val url: String,
        override val properties: Map<String, String>,
        override val body: String? = null
    ) : HttpRequest {
        override val method = "POST"
    }
}