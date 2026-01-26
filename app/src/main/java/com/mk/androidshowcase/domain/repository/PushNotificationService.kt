package com.mk.androidshowcase.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import com.mk.androidshowcase.domain.model.Notification

interface PushNotificationService {
    val token: StateFlow<String?>
    val notifications: Flow<Notification>
    val deepLinks: Flow<String>

    fun getPermissionStatus(): PushPermissionStatus
    suspend fun requestPermission(): PushPermissionStatus
    suspend fun refreshToken()
    fun logToken()
    suspend fun updateToken(token: String)
    fun onNotificationReceived(title: String?, body: String?, data: Map<String, String>)
    fun onDeepLinkReceived(deepLink: String)
}

enum class PushPermissionStatus {
    GRANTED,
    DENIED,
    NOT_DETERMINED
}
