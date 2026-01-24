package mk.digital.androidshowcase.data.notification

import mk.digital.androidshowcase.data.local.preferences.PersistentPreferences
import mk.digital.androidshowcase.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val persistentPreferences: PersistentPreferences,
) : NotificationRepository {

    override suspend fun getToken(): String? = persistentPreferences.getFcmToken()

    override suspend fun setToken(token: String): Unit = persistentPreferences.setFcmToken(token)
}
