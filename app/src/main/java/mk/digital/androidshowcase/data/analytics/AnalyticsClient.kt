package mk.digital.androidshowcase.data.analytics

interface AnalyticsClient {
    fun trackScreen(screenName: String)
    fun recordException(throwable: Throwable)
    fun log(message: String)

    companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }
}
