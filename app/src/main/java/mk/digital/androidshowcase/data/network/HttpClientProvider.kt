package mk.digital.androidshowcase.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

 class HttpClientProvider {
     fun create(): HttpClient = HttpClient(OkHttp) {
        applyCommonConfig()
    }
}

fun HttpClientConfig<*>.applyCommonConfig() {
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
        }
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }

    install(Logging) {
        logger = KtorLogger
        level = LogLevel.BODY
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIME_OUT_MILLIS
        connectTimeoutMillis = CONNECT_TIME_OUT_MILLIS
    }
}

private const val BASE_URL = "jsonplaceholder.typicode.com"
private const val REQUEST_TIME_OUT_MILLIS: Long = 30_000
private const val CONNECT_TIME_OUT_MILLIS: Long = 30_000

private object KtorLogger : Logger {
    override fun log(message: String) {
        message.chunked(LOG_CHUNK_SIZE).forEach { println("HTTP: $it") }
    }

    private const val LOG_CHUNK_SIZE = 4000
}

