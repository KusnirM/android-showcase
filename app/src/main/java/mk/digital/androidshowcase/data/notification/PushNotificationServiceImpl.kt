package mk.digital.androidshowcase.data.notification

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.repository.NotificationRepository
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.repository.PushPermissionStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationServiceImpl @Inject constructor(
    @param:ApplicationContext private val context: android.content.Context,
    private val notificationRepository: NotificationRepository,
) : PushNotificationService {

    private val _token = MutableStateFlow<String?>(null)
    override val token: StateFlow<String?> = _token.asStateFlow()

    private val _notifications = MutableSharedFlow<Notification>()
    override val notifications: Flow<Notification> = _notifications.asSharedFlow()

    private val _deepLinks = MutableSharedFlow<String>()
    override val deepLinks: Flow<String> = _deepLinks.asSharedFlow()

    init {
        loadSavedToken()
    }

    private fun loadSavedToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            _token.value = token
        }
    }

    override fun getPermissionStatus(): PushPermissionStatus {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> PushPermissionStatus.GRANTED
                else -> PushPermissionStatus.NOT_DETERMINED
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
            FirebaseMessaging.getInstance().deleteToken().await()
            val newToken = FirebaseMessaging.getInstance().token.await()
            _token.value = newToken
            notificationRepository.setToken(newToken)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to refresh token", e)
        }
    }

    override fun logToken() {
        Log.d(TAG, "FCM Token: ${_token.value}")
    }

    suspend fun onNewToken(token: String) {
        _token.value = token
        notificationRepository.setToken(token)
    }

    suspend fun onNotificationReceived(notification: Notification) {
        _notifications.emit(notification)
    }

    suspend fun onDeepLinkReceived(deepLink: String) {
        _deepLinks.emit(deepLink)
    }

    private companion object {
        private const val TAG = "PushNotificationService"
    }
}
