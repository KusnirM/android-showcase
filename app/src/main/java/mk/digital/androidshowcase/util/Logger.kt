package mk.digital.androidshowcase.util

import android.util.Log
import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import javax.inject.Inject

class Logger @Inject constructor(
    private val analyticsClient: AnalyticsClient
) {

    fun e(log: String) {
        Log.e(TAG, log)
    }

    fun e(e: Throwable) {
        Log.e(TAG, e.stackTraceToString())
        analyticsClient.recordException(e)
    }

    fun e(log: String, e: Throwable) {
        Log.e(TAG, log, e)
        analyticsClient.recordException(e)
    }

    fun d(log: String) {
        Log.d(TAG, log)
    }

    companion object {
        private const val TAG = "Logger"
    }
}
