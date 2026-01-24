package mk.digital.androidshowcase.domain.repository

import mk.digital.androidshowcase.domain.model.Notification

interface LocalNotificationService {
    fun showNotification(notification: Notification)
    fun cancelNotification(id: String)
    fun cancelAllNotifications()
}
