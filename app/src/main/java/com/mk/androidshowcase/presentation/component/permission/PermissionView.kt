package com.mk.androidshowcase.presentation.component.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mk.androidshowcase.R
import com.mk.androidshowcase.presentation.component.AppAlertDialog
import com.mk.androidshowcase.presentation.component.buttons.ContainedButton
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer4
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import com.mk.androidshowcase.presentation.component.text.titleLarge.TextTitleLargePrimary
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun PermissionView(
    permission: PermissionType,
    onDeniedDialogDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    when (permission) {
        PermissionType.GALLERY -> content()
        PermissionType.CAMERA,
        PermissionType.LOCATION,
        PermissionType.NOTIFICATION -> PermissionContendDefault(
            permission = permission,
            onDeniedDialogDismiss = onDeniedDialogDismiss,
            content = content,
        )
    }
}

private fun PermissionType.toManifestPermission() = when (this) {
    PermissionType.CAMERA -> Manifest.permission.CAMERA
    PermissionType.GALLERY -> null
    PermissionType.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
    PermissionType.NOTIFICATION -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.POST_NOTIFICATIONS
    } else null
}

@Composable
private fun PermissionContendDefault(
    permission: PermissionType,
    onDeniedDialogDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    val manifestPermission = permission.toManifestPermission()
    if (manifestPermission == null) {
        content()
        return
    }

    val state = rememberPermissionState(manifestPermission)
    var rationaleDismissed by remember { mutableStateOf(false) }
    var hasRequested by remember { mutableStateOf(false) }

    if (state.status.isGranted) {
        content()
    } else if (state.status.shouldShowRationale && !rationaleDismissed) {
        PermissionRationaleUi(
            message = permission.rationaleMessage,
            onConfirm = { state.launchPermissionRequest() },
            onDismiss = {
                rationaleDismissed = true
                onDeniedDialogDismiss()
            }
        )
    } else {
        LaunchedEffect(manifestPermission, hasRequested) {
            if (!hasRequested && !rationaleDismissed) {
                hasRequested = true
                state.launchPermissionRequest()
            }
        }
        val context = LocalContext.current
        PermissionDenyUi(
            message = permission.deniedMessage,
            onConfirm = { launchSettings(context) },
        )
    }
}

private fun launchSettings(context: Context) {
    Intent(
        /* action = */ Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        /* uri = */ Uri.fromParts("package", context.packageName, null)
    ).also {
        context.startActivity(it)
    }
}

@Composable
private fun PermissionRationaleUi(
    @StringRes message: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AppAlertDialog(
        title = stringResource(R.string.permission_required),
        text = stringResource(message),
        confirmButtonText = stringResource(R.string.permission_allow),
        dismissButtonText = stringResource(R.string.permission_cancel),
        onConfirm = onConfirm,
        onDismissRequest = onDismiss,
    )
}

@Composable
fun PermissionDenyUi(
    @StringRes message: Int,
    onConfirm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(space4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitleLargePrimary(
            text = stringResource(R.string.permission_required),
            textAlign = TextAlign.Center
        )
        Spacer4()
        TextBodyLargeNeutral80(
            text = stringResource(message),
            textAlign = TextAlign.Center
        )
        Spacer4()
        ContainedButton(
            id = R.string.permission_allow,
            onClick = onConfirm,
            modifier = Modifier.fillMaxWidth()
        )
    }
}



