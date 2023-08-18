package com.metaplex.ktorDriver

import com.metaplex.networking.HttpNetworkDriver
import com.metaplex.networking.HttpRequest
import com.metaplex.networking.Rpc20Driver
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod

class kTorDriver(val ktorHttpClient: HttpClient) : HttpNetworkDriver {
    override suspend fun makeHttpRequest(request: HttpRequest): String {
        val response = ktorHttpClient.request(request.url) {
            request.properties.forEach { (key, value) ->
                headers.append(key, value)
            }
            method = HttpMethod(request.method)
            setBody(request.body)
        }
        return response.bodyAsText()
    }
}