package com.mk.androidshowcase.data.notification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.tasks.await
import com.mk.androidshowcase.data.analytics.AnalyticsClient
import com.mk.androidshowcase.domain.model.Notification
import com.mk.androidshowcase.domain.model.NotificationChannel
import com.mk.androidshowcase.domain.repository.NotificationRepository
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.repository.PushPermissionStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationServiceImpl @Inject constructor(
    @param:ApplicationContext private val context: android.content.Context,
    private val notificationRepository: NotificationRepository,
    private val analyticsClient: AnalyticsClient
) : PushNotificationService {

    private val _token = MutableStateFlow<String?>(null)
    override val token: StateFlow<String?> = _token.asStateFlow()

    private val _notifications = MutableSharedFlow<Notification>()
    override val notifications: Flow<Notification> = _notifications.asSharedFlow()

    private val _deepLinks = Channel<String>(Channel.BUFFERED)
    override val deepLinks: Flow<String> = _deepLinks.receiveAsFlow()

    init {
        loadSavedToken()
    }

    private fun loadSavedToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            _token.value = token
        }
    }

    override fun getPermissionStatus(): PushPermissionStatus {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> PushPermissionStatus.GRANTED

                else -> PushPermissionStatus.DENIED
            }
        } else {
            PushPermissionStatus.GRANTED
        }
    }

    override suspend fun requestPermission(): PushPermissionStatus {
        return getPermissionStatus()
    }

    override suspend fun refreshToken() {
        try {
            val token = FirebaseMessaging.getInstance().token.await()
            updateToken(token)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to refresh token", e)
            analyticsClient.recordException(e)
        }
    }

    override fun logToken() {
        Log.d(TAG, "FCM Token: ${_token.value}")
    }

    override suspend fun updateToken(token: String) {
        _token.value = token
        notificationRepository.setToken(token)
        Log.d(TAG, "FCM Token updated: ${token.take(TOKEN_PREFIX_LENGTH)}...")
        analyticsClient.log("FCM token updated")
    }

    override fun onNotificationReceived(
        title: String?,
        body: String?,
        data: Map<String, String>
    ) {
        val deepLink = data[KEY_DEEP_LINK]

        val notification = Notification(
            id = data[KEY_NOTIFICATION_ID] ?: System.currentTimeMillis().toString(),
            title = title ?: data[KEY_TITLE] ?: DEFAULT_TITLE,
            message = body ?: data[KEY_BODY] ?: data[KEY_MESSAGE] ?: "",
            channel = NotificationChannel.GENERAL,
            data = data,
            deepLink = deepLink
        )

        _notifications.tryEmit(notification)
        deepLink?.let { _deepLinks.trySend(it) }

        analyticsClient.log("Push notification received: ${notification.title}")
    }

    override fun onDeepLinkReceived(deepLink: String) {
        _deepLinks.trySend(deepLink)
    }

    companion object {
        private const val TAG = "PushNotificationService"
        private const val TOKEN_PREFIX_LENGTH = 10
        private const val KEY_DEEP_LINK = "deep_link"
        private const val KEY_NOTIFICATION_ID = "notificationId"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "body"
        private const val KEY_MESSAGE = "message"
        private const val DEFAULT_TITLE = "Notification"
    }
}
