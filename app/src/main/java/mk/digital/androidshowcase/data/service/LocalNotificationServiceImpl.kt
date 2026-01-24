package mk.digital.androidshowcase.data.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import kotlin.hashCode

class LocalNotificationServiceImpl(
    private val context: Context,
) : LocalNotificationService {

    private var channelsInitialized = false

    private val manager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showNotification(notification: Notification) {
        if (!hasPermission()) {
            Log.w(TAG, "Notification permission not granted")
            return
        }
        ensureChannelsInitialized()

        try {
            val pendingIntent = createPendingIntent(notification)

            val builder = NotificationCompat.Builder(context, notification.channel.id)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setPriority(getPriority(notification.channel))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle(notification.title)
                .setContentText(notification.message)
                .setContentIntent(pendingIntent)

            val notificationId = notification.id.hashCode()
            manager.notify(notificationId, builder.build())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to show notification", e)
        }
    }

    override fun cancelNotification(id: String) {
        manager.cancel(id.hashCode())
    }

    override fun cancelAllNotifications() {
        manager.cancelAll()
    }

    private fun ensureChannelsInitialized() {
        if (!channelsInitialized) {
            initChannels()
            channelsInitialized = true
        }
    }

    private fun initChannels() {
        AppNotificationChannel.entries.forEach { channel ->
            val importance = when (channel) {
                AppNotificationChannel.GENERAL -> NotificationManager.IMPORTANCE_DEFAULT
                AppNotificationChannel.REMINDERS -> NotificationManager.IMPORTANCE_HIGH
                AppNotificationChannel.PROMOTIONS -> NotificationManager.IMPORTANCE_LOW
            }

            val notificationChannel = NotificationChannel(
                channel.id,
                channel.channelName,
                importance
            ).apply {
                description = channel.description
            }
            manager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getPriority(channel: AppNotificationChannel): Int {
        return when (channel) {
            AppNotificationChannel.GENERAL -> NotificationCompat.PRIORITY_DEFAULT
            AppNotificationChannel.REMINDERS -> NotificationCompat.PRIORITY_HIGH
            AppNotificationChannel.PROMOTIONS -> NotificationCompat.PRIORITY_LOW
        }
    }

    private fun createPendingIntent(notification: Notification): PendingIntent? {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            ?: return null

        launchIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            notification.deepLink?.let { putExtra(EXTRA_DEEP_LINK, it) }
            putExtra(EXTRA_NOTIFICATION_ID, notification.id)
        }

        return PendingIntent.getActivity(
            context,
            notification.id.hashCode(),
            launchIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    companion object {
        private const val TAG = "LocalNotificationService"
        const val EXTRA_DEEP_LINK = "notification_deep_link"
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }
}
