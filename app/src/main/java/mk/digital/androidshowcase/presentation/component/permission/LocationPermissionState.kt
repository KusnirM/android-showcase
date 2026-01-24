package mk.digital.androidshowcase.presentation.component.permission

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

data class LocationPermissionState(
    val isGranted: Boolean,
    val shouldShowRationale: Boolean,
    val requestPermission: () -> Unit,
)

@Composable
fun rememberLocationPermissionState(): LocationPermissionState {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    return LocationPermissionState(
        isGranted = permissionState.status.isGranted,
        shouldShowRationale = permissionState.status.shouldShowRationale,
        requestPermission = permissionState::launchPermissionRequest
    )
}
