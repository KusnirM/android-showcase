package mk.digital.androidshowcase.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.model.NotificationChannel
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseMessagingService : FirebaseMessagingService() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var localNotificationService: LocalNotificationService

    @Inject
    lateinit var pushNotificationService: PushNotificationService

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM token: $token")

        coroutineScope.launch {
            pushNotificationService.updateToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "Message received from: ${remoteMessage.from}")

        val id = remoteMessage.data[KEY_NOTIFICATION_ID] ?: System.currentTimeMillis().toString()

        val title = remoteMessage.notification?.title
            ?: remoteMessage.data[KEY_TITLE]
            ?: DEFAULT_TITLE

        val body = remoteMessage.notification?.body
            ?: remoteMessage.data[KEY_BODY]
            ?: remoteMessage.data[KEY_MESSAGE]
            ?: ""

        val deepLink = remoteMessage.data[KEY_DEEP_LINK]

        // Notify PushNotificationService for flow subscribers
        pushNotificationService.onNotificationReceived(title = title, body = body, data = remoteMessage.data)

        // Show local notification (handles foreground case)
        val notification = Notification(
            id = id,
            title = title,
            message = body,
            channel = NotificationChannel.GENERAL,
            data = remoteMessage.data,
            deepLink = deepLink
        )
        localNotificationService.showNotification(notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    companion object {
        private const val TAG = "FCMService"
        private const val KEY_DEEP_LINK = "deep_link"
        private const val KEY_NOTIFICATION_ID = "notificationId"
        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "body"
        private const val KEY_MESSAGE = "message"
        private const val DEFAULT_TITLE = "Notification"
    }
}
