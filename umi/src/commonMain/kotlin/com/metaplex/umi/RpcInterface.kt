package com.metaplex.umi

import com.metaplex.umi_public_keys.PublicKey

/**
 * Defines the interface for an RPC client.
 * It allows us to interact with the Solana blockchain.
 *
 * @category Context and Interfaces
 */
interface RpcInterface {
    /** The RPC endpoint used by the client. */
    fun getEndpoint(): String

    /** The Solana cluster of the RPC being used. */
    fun getCluster(): Cluster

    /**
     * Whether or not an account at a given address exists.
     *
     * @param publicKey The public key of the account.
     * @param options The options to use when checking if an account exists.
     * @returns `true` if the account exists, `false` otherwise.
     */
    suspend fun accountExists( publicKey: PublicKey): Boolean
}

class NullRpc: RpcInterface {
    override fun getEndpoint(): String {
        throw NotImplementedError("RpcInterface")
    }

    override fun getCluster(): Cluster {
        throw NotImplementedError("RpcInterface")
    }

    override suspend fun accountExists( publicKey: PublicKey): Boolean {
        throw NotImplementedError("RpcInterface")
    }
}
fun RpcInterface.createNullRpc(): RpcInterface {
    return NullRpc()
}