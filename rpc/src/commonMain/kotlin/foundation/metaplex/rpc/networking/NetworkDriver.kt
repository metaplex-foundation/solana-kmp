package foundation.metaplex.rpc.networking

import com.funkatronics.networking.HttpNetworkDriver
import com.funkatronics.networking.HttpRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

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
            // Workaround for RPC Response
            // Removes a specific portion from the request body if present. RPCs complain about it
            setBody(request.body?.replace("\"type\":\"com.funkatronics.rpccore.JsonRpc20Request\",", ""))
        }
        return response.bodyAsText()
    }
}