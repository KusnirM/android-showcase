package com.mk.androidshowcase.data.notification

import com.mk.androidshowcase.data.local.preferences.PersistentPreferences
import com.mk.androidshowcase.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val persistentPreferences: PersistentPreferences,
) : NotificationRepository {

    override suspend fun getToken(): String? = persistentPreferences.getFcmToken()

    override suspend fun setToken(token: String): Unit = persistentPreferences.setFcmToken(token)
}
