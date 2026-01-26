package com.mk.androidshowcase.domain.repository

import com.mk.androidshowcase.domain.model.Notification

interface LocalNotificationService {
    fun showNotification(notification: Notification)
    fun cancelNotification(id: String)
    fun cancelAllNotifications()
}
