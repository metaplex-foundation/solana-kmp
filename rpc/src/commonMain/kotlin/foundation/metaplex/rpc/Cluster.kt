package foundation.metaplex.rpc


/**
 * Defines the available Solana clusters.
 * @category Utils — Cluster
 */
sealed class Cluster {
    object MainnetBeta : Cluster()
    object Devnet : Cluster()
    object Testnet : Cluster()
    object Localnet : Cluster()
    object Custom : Cluster()

    companion object {
        fun fromString(clusterName: String): Cluster {
            return when (clusterName) {
                "mainnet-beta" -> MainnetBeta
                "devnet" -> Devnet
                "testnet" -> Testnet
                "localnet" -> Localnet
                "custom" -> Custom
                else -> throw IllegalArgumentException("Unknown cluster: $clusterName")
            }
        }
    }
}

val MAINNET_BETA_DOMAINS = listOf(
    "api.mainnet-beta.solana.com",
    "ssc-dao.genesysgo.net",
)

val DEVNET_DOMAINS = listOf(
    "api.devnet.solana.com",
    "psytrbhymqlkfrhudd.dev.genesysgo.net",
)

val TESTNET_DOMAINS = listOf("api.testnet.solana.com")
val LOCALNET_DOMAINS = listOf("localhost", "127.0.0.1")

/**
 * Helper method that tries its best to resolve a cluster from a given endpoint.
 * @category Utils — Cluster
 */
fun resolveClusterFromEndpoint(endpoint: String) : Cluster {
    val domain = endpoint
    if (MAINNET_BETA_DOMAINS.contains(domain)) return Cluster.MainnetBeta
    if (DEVNET_DOMAINS.contains(domain)) return Cluster.Devnet
    if (TESTNET_DOMAINS.contains(domain)) return Cluster.Testnet
    if (LOCALNET_DOMAINS.contains(domain)) return Cluster.Localnet
    if (endpoint.contains("mainnet")) return Cluster.MainnetBeta
    if (endpoint.contains("dev net")) return Cluster.Devnet
    if (endpoint.contains("testnet")) return Cluster.Testnet
    if (endpoint.contains("local")) return Cluster.Localnet
    return Cluster.Custom
}