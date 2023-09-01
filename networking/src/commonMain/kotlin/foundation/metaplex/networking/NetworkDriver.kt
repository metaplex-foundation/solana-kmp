package foundation.metaplex.networking

import com.funkatronics.networking.HttpNetworkDriver
import com.funkatronics.networking.HttpRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod

class NetworkDriver(private val httpClient: HttpClient = NetworkClient()) : HttpNetworkDriver {
    override suspend fun makeHttpRequest(request: HttpRequest): String {
        val response = httpClient.request(request.url) {
            request.properties.forEach { (key, value) ->
                headers.append(key, value)
            }
            method = HttpMethod(request.method)
            setBody(request.body)
        }
        return response.bodyAsText()
    }
}