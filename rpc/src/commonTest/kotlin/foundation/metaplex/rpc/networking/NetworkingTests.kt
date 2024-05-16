package foundation.metaplex.rpc.networking

import com.solana.networking.HttpRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

data class MockHttpRequest(
    override val url: String,
    override val method: String,
    override val properties: Map<String, String>,
    override val body: String?,
) : HttpRequest

class NetworkingTests {
    @Test
    fun testNetworkingHttpRequest() = runTest {
        val networkDriver = NetworkDriver()
        val request = MockHttpRequest(
            "https://api.mainnet-beta.solana.com/",
            "get",
            mapOf("html" to "Content-Type"),
            "echo"
        )
        val response = networkDriver.makeHttpRequest(request)
        assertNotNull(response)
    }
}