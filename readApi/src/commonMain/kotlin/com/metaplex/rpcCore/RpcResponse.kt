package com.metaplex.rpcCore

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

interface RpcResponse<R> {
    val result: R?
    val error: RpcError?
    val id: String?
    val jsonrpc: String
}

typealias DefaultRpcResponse = RpcResponse<JsonElement>

@Serializable
data class RpcError(val code: Int, val message: String)

@Serializable
open class Rpc20Response<R>(
    override val result: R? = null,
    override val error: RpcError? = null,
    override val id: String? = null
) : RpcResponse<R> {
    override val jsonrpc = "2.0"
}