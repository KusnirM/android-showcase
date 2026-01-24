package mk.digital.androidshowcase.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import mk.digital.androidshowcase.domain.model.Notification

interface PushNotificationService {
    val token: StateFlow<String?>
    val notifications: Flow<Notification>
    val deepLinks: Flow<String>

    fun getPermissionStatus(): PushPermissionStatus
    suspend fun requestPermission(): PushPermissionStatus
    suspend fun refreshToken()
    fun logToken()
}

enum class PushPermissionStatus {
    GRANTED,
    DENIED,
    NOT_DETERMINED
}
