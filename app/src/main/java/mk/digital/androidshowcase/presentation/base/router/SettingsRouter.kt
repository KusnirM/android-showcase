package mk.digital.androidshowcase.presentation.base.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

interface SettingsRouter {
    fun openSettings()
    fun openNotificationSettings()
    class Impl(private val context: Context) : SettingsRouter {
        override fun openSettings() {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }

        override fun openNotificationSettings() {
            val intent =
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            context.startActivity(intent)
        }
    }
}
