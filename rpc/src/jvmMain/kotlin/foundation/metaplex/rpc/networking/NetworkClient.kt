package foundation.metaplex.rpc.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual fun NetworkClient(): HttpClient = HttpClient(CIO)