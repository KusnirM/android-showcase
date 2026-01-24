package mk.digital.androidshowcase.domain.exceptions.base

abstract class BaseException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    abstract val errorCode: String
    abstract val userMessage: String
    open val shouldReport: Boolean = true
}

object NetworkErrorCode {
    const val UNKNOWN = "1000"
    const val NO_CONNECTION = "1001"
    const val TIMEOUT = "1002"
}

class NetworkException(
    message: String = "Network error occurred",
    cause: Throwable? = null,
    override val userMessage: String = "Please check your internet connection",
    override val errorCode: String = NetworkErrorCode.UNKNOWN
) : BaseException(message, cause)

class ApiException(
    val httpCode: Int,
    message: String,
    cause: Throwable? = null,
    override val userMessage: String = "Something went wrong. Please try again."
) : BaseException(message, cause) {
    override val errorCode: String = "2-$httpCode"
}

object DataErrorCode {
    const val UNKNOWN = "3000"
    const val PARSING = "3001"
    const val SERIALIZATION = "3002"
}

class DataException(
    message: String = "Data parsing error",
    cause: Throwable? = null,
    override val userMessage: String = "Unable to process data",
    override val errorCode: String = DataErrorCode.UNKNOWN
) : BaseException(message, cause)

class UnknownException(
    cause: Throwable? = null,
    override val userMessage: String = "An unexpected error occurred",
    override val errorCode: String = "9000"
) : BaseException(cause?.message ?: "Unknown error", cause)

object LocationErrorCode {
    const val UNKNOWN = "4000"
    const val NOT_AVAILABLE = "4001"
    const val PERMISSION_DENIED = "4002"
}

class LocationException(
    message: String = "Location error occurred",
    cause: Throwable? = null,
    override val userMessage: String = "Unable to get location",
    override val errorCode: String = LocationErrorCode.UNKNOWN
) : BaseException(message, cause)
