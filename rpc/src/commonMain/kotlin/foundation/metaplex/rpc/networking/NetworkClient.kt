package foundation.metaplex.rpc.networking

import io.ktor.client.HttpClient

// Standard NetworkClient. Used as Default
expect fun NetworkClient(): HttpClient//  = HttpClient()
