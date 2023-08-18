package com.metaplex.rpcCore

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
sealed interface RpcRequest {
    val method: String
    val params: JsonElement?
    val jsonrpc: String
    val id: String
}

@Serializable
open class JsonRpcRequest (
    override val method: String,
    override val params: JsonElement?,
    override val id: String,
    override val jsonrpc: String
) : RpcRequest

@Serializable
open class JsonRpc20Request (
    override val method: String,
    override val params: JsonElement? = null,
    override val id: String,
) : RpcRequest {
    override val jsonrpc: String = "2.0"
}
