package com.mk.androidshowcase.presentation.component.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.mk.androidshowcase.domain.repository.PushPermissionStatus

class NotificationPermissionRequester(
    val request: () -> Unit
)

@Composable
fun rememberNotificationPermissionRequester(
    onResult: (PushPermissionStatus) -> Unit
): NotificationPermissionRequester {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onResult(if (isGranted) PushPermissionStatus.GRANTED else PushPermissionStatus.DENIED)
    }

    return remember {
        NotificationPermissionRequester {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val currentStatus = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )
                if (currentStatus == PackageManager.PERMISSION_GRANTED) {
                    onResult(PushPermissionStatus.GRANTED)
                } else {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                onResult(PushPermissionStatus.GRANTED)
            }
        }
    }
}

