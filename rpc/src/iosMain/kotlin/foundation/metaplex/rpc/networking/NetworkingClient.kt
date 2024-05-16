package foundation.metaplex.rpc.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun NetworkClient(): HttpClient = HttpClient(Darwin)