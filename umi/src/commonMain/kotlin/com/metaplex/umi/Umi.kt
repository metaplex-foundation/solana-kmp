package com.metaplex.umi

interface UmiPlugin {
    fun install(umi: Umi)
}

interface Context {
    /** The signer using your app. */
    val identity: Signer
    /** The signer paying for things, usually the same as the `identity`. */
    val payer: Signer
    /** An interface for sending RPC requests. */
    val rpc: RpcInterface
}

interface Umi: Context {
    fun use(plugin: UmiPlugin): Umi
}