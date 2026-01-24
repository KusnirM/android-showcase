package mk.digital.androidshowcase.data.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import mk.digital.androidshowcase.domain.exceptions.base.ApiException
import mk.digital.androidshowcase.domain.exceptions.base.DataErrorCode
import mk.digital.androidshowcase.domain.exceptions.base.DataException
import mk.digital.androidshowcase.domain.exceptions.base.NetworkErrorCode
import mk.digital.androidshowcase.domain.exceptions.base.NetworkException

suspend inline fun <T> handleApiCall(
    crossinline call: suspend () -> T
): T = try {
    call()
} catch (e: HttpRequestTimeoutException) {
    throw NetworkException(
        message = "Request timeout: ${e.message}",
        cause = e,
        userMessage = "Request timed out. Please try again.",
        errorCode = NetworkErrorCode.TIMEOUT
    )
} catch (e: IOException) {
    throw NetworkException(
        message = "Network error: ${e.message}",
        cause = e,
        errorCode = NetworkErrorCode.NO_CONNECTION
    )
} catch (e: ClientRequestException) {
    throw ApiException(
        httpCode = e.response.status.value,
        message = "Client error: ${e.response.status.description}",
        cause = e,
        userMessage = when (e.response.status.value) {
            401 -> "Please log in again."
            403 -> "You don't have permission to access this."
            404 -> "The requested resource was not found."
            else -> "Request failed. Please try again."
        }
    )
} catch (e: ServerResponseException) {
    throw ApiException(
        httpCode = e.response.status.value,
        message = "Server error: ${e.response.status.description}",
        cause = e,
        userMessage = "Server error. Please try again later."
    )
} catch (e: JsonConvertException) {
    throw DataException(
        message = "JSON parsing error: ${e.message}",
        cause = e,
        errorCode = DataErrorCode.PARSING
    )
} catch (e: SerializationException) {
    throw DataException(
        message = "Serialization error: ${e.message}",
        cause = e,
        errorCode = DataErrorCode.SERIALIZATION
    )
}
