package mk.digital.androidshowcase.presentation.screen.apis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.data.biometric.BiometricResult
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.base.lifecycleAwareViewModel
import mk.digital.androidshowcase.presentation.component.buttons.OutlinedButton
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.permission.rememberLocationPermissionState
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.screen.LocalSnackbarHostState
import mk.digital.androidshowcase.util.StringFormatter

private enum class PendingLocationAction { NONE, GET_LOCATION, START_UPDATES }

@Composable
private fun rememberLocationActionHandler(
    onGetLocation: () -> Unit,
    onStartUpdates: () -> Unit
): (PendingLocationAction) -> Unit {
    val locationPermission = rememberLocationPermissionState()
    var pendingAction by remember { mutableStateOf(PendingLocationAction.NONE) }

    LaunchedEffect(locationPermission.isGranted, pendingAction) {
        if (locationPermission.isGranted && pendingAction != PendingLocationAction.NONE) {
            when (pendingAction) {
                PendingLocationAction.GET_LOCATION -> onGetLocation()
                PendingLocationAction.START_UPDATES -> onStartUpdates()
                PendingLocationAction.NONE -> Unit
            }
            pendingAction = PendingLocationAction.NONE
        }
    }

    return { action ->
        if (locationPermission.isGranted) {
            when (action) {
                PendingLocationAction.GET_LOCATION -> onGetLocation()
                PendingLocationAction.START_UPDATES -> onStartUpdates()
                PendingLocationAction.NONE -> Unit
            }
        } else {
            pendingAction = action
            locationPermission.requestPermission()
        }
    }
}

@Suppress("CyclomaticComplexMethod", "CognitiveComplexMethod")
@Composable
fun ApisScreen(
    router: NavRouter<Route>,
    viewModel: ApisViewModel = lifecycleAwareViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val copiedMessage = stringResource(R.string.platform_apis_copied_message)

    val handleLocationAction = rememberLocationActionHandler(
        onGetLocation = viewModel::getLocation,
        onStartUpdates = viewModel::startLocationUpdates
    )

    LaunchedEffect(state.copiedToClipboard) {
        if (state.copiedToClipboard) {
            snackbarHostState.showSnackbar(copiedMessage)
            delay(100)
            viewModel.resetCopyState()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace
        ),
        verticalArrangement = Arrangement.spacedBy(space4)
    ) {
        item {
            Column {
                TextHeadlineMediumPrimary(stringResource(R.string.platform_apis_title))
                TextBodyMediumNeutral80(stringResource(R.string.platform_apis_subtitle))
            }
        }

        item {
            ApiCard(
                icon = Icons.Outlined.Share,
                title = stringResource(R.string.platform_apis_share_title)
            ) {
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_share_action),
                    onClick = viewModel::share
                )
            }
        }

        item {
            ApiCard(
                icon = Icons.Outlined.Phone,
                title = stringResource(R.string.platform_apis_dial_title)
            ) {
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_dial_action),
                    onClick = viewModel::dial
                )
            }
        }

        item {
            ApiCard(
                icon = Icons.Outlined.Link,
                title = stringResource(R.string.platform_apis_link_title)
            ) {
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_link_action),
                    onClick = viewModel::openLink
                )
            }
        }

        item {
            ApiCard(
                icon = Icons.Outlined.Email,
                title = stringResource(R.string.platform_apis_email_title)
            ) {
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_email_action),
                    onClick = viewModel::sendEmail
                )
            }
        }

        item {
            ApiCard(
                icon = Icons.Outlined.ContentCopy,
                title = stringResource(R.string.platform_apis_copy_title)
            ) {
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_copy_action),
                    onClick = viewModel::copyToClipboard
                )
            }
        }

        item {
            val loadingText = stringResource(R.string.platform_apis_location_loading)
            val errorText = stringResource(R.string.platform_apis_location_error)
            val location = state.location
            ApiCard(
                icon = Icons.Outlined.LocationOn,
                title = stringResource(R.string.platform_apis_location_title)
            ) {
                when {
                    state.locationLoading -> TextBodyMediumNeutral80(loadingText)
                    state.locationError -> TextBodyMediumNeutral80(errorText)
                    location != null -> TextBodyMediumNeutral80(
                        formatLocationText(location.lat, location.lon)
                    )
                }
                Spacer2()
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_location_action),
                    onClick = { handleLocationAction(PendingLocationAction.GET_LOCATION) }
                )
            }
        }

        item {
            val errorText = stringResource(R.string.platform_apis_location_updates_error)
            val trackedLocation = state.trackedLocation
            ApiCard(
                icon = Icons.Outlined.MyLocation,
                title = stringResource(R.string.platform_apis_location_updates_title)
            ) {
                when {
                    state.locationUpdatesError -> TextBodyMediumNeutral80(errorText)
                    trackedLocation != null -> TextBodyMediumNeutral80(
                        formatLocationText(trackedLocation.lat, trackedLocation.lon)
                    )
                }
                Spacer2()
                ApiCardButton(
                    text = stringResource(
                        if (state.isTrackingLocation) R.string.platform_apis_location_updates_stop
                        else R.string.platform_apis_location_updates_start
                    ),
                    onClick = {
                        if (state.isTrackingLocation) viewModel.stopLocationUpdates()
                        else handleLocationAction(PendingLocationAction.START_UPDATES)
                    }
                )
            }
        }

        item {
            val successText = stringResource(R.string.platform_apis_biometrics_success)
            val failedText = stringResource(R.string.platform_apis_biometrics_failed)
            val cancelledText = stringResource(R.string.platform_apis_biometrics_cancelled)
            val notAvailableText = stringResource(R.string.platform_apis_biometrics_not_available)
            val activityNotAvailableText = stringResource(R.string.platform_apis_biometrics_activity_not_available)
            val unknownErrorText = stringResource(R.string.platform_apis_biometrics_unknown_error)
            ApiCard(
                icon = Icons.Outlined.Fingerprint,
                title = stringResource(R.string.platform_apis_biometrics_title)
            ) {
                when {
                    !state.biometricsAvailable -> TextBodyMediumNeutral80(notAvailableText)
                    state.biometricsLoading -> TextBodyMediumNeutral80("...")
                    state.biometricsResult is BiometricResult.Success -> TextBodyMediumNeutral80(successText)
                    state.biometricsResult is BiometricResult.SystemError -> {
                        val errorMsg = (state.biometricsResult as BiometricResult.SystemError)
                            .message.ifEmpty { unknownErrorText }
                        TextBodyMediumNeutral80("$failedText: $errorMsg")
                    }

                    state.biometricsResult is BiometricResult.Cancelled -> TextBodyMediumNeutral80(cancelledText)
                    state.biometricsResult is BiometricResult.NotAvailable -> TextBodyMediumNeutral80(notAvailableText)
                    state.biometricsResult is BiometricResult.ActivityNotAvailable ->
                        TextBodyMediumNeutral80(activityNotAvailableText)
                }
                Spacer2()
                ApiCardButton(
                    text = stringResource(R.string.platform_apis_biometrics_action),
                    onClick = viewModel::authenticateWithBiometrics,
                    enabled = state.biometricsAvailable && !state.biometricsLoading
                )
            }
        }
    }

    ApisNavEvents(router, viewModel.navEvent)
}

@Composable
private fun ApiCard(
    icon: ImageVector,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    AppElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(space4))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                content = {
                    TextBodyLargeNeutral80(title)
                    Spacer2()
                    content()
                }
            )
        }
    }
}

@Composable
private fun ApiCardButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        text = text,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    )
}

private const val COORDINATE_DECIMAL_PLACES = 6

@Composable
private fun formatLocationText(lat: Double, lon: Double): String {
    return stringResource(
        R.string.platform_apis_location_result,
        StringFormatter.formatDouble(lat, COORDINATE_DECIMAL_PLACES),
        StringFormatter.formatDouble(lon, COORDINATE_DECIMAL_PLACES)
    )
}

@Composable
private fun ApisNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>,
) {
    val context = LocalContext.current
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is ApisNavEvent.Share -> router.share(context.getString(event.textRes))
            is ApisNavEvent.Dial -> router.dial(context.getString(event.numberRes))
            is ApisNavEvent.OpenLink -> router.openLink(context.getString(event.urlRes))
            is ApisNavEvent.SendEmail -> router.sendEmail(
                context.getString(event.toRes),
                context.getString(event.subjectRes),
                context.getString(event.bodyRes)
            )
            is ApisNavEvent.CopyToClipboard -> router.copyToClipboard(context.getString(event.textRes))
        }
    }
}
