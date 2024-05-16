package foundation.metaplex.rpc.networking

import com.solana.networking.HttpNetworkDriver
import com.solana.networking.HttpRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod

/**
 * Represents a network driver for making HTTP requests using an HttpClient.
 *
 * @param httpClient An optional HttpClient used for making HTTP requests. Defaults to a standard NetworkClient.
 */
class NetworkDriver(private val httpClient: HttpClient = NetworkClient()) : HttpNetworkDriver {

    /**
     * Makes an HTTP request and returns the response as a string.
     *
     * @param request The HttpRequest object representing the HTTP request to be made.
     * @return The response body as a string.
     */
    override suspend fun makeHttpRequest(request: HttpRequest): String {
        val response = httpClient.request(request.url) {
            // Set HTTP request properties, including headers and method
            request.properties.forEach { (key, value) ->
                headers.append(key, value)
            }
            method = HttpMethod(request.method)
            setBody(request.body)
        }
        println(response.bodyAsText())
        return response.bodyAsText()
    }
}