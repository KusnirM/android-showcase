package mk.digital.androidshowcase.presentation.screen.notifications

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
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.domain.repository.PushPermissionStatus
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.component.buttons.OutlinedButton
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.permission.rememberNotificationPermissionRequester
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun NotificationsScreen(
    router: NavRouter<Route>,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissionRequester = rememberNotificationPermissionRequester { status ->
        viewModel.updatePermissionStatus(status)
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
                TextHeadlineMediumPrimary(stringResource(R.string.notifications_title))
                TextBodyMediumNeutral80(stringResource(R.string.notifications_subtitle))
            }
        }

        // Permission Card
        item {
            val permissionText = when (state.permissionStatus) {
                PushPermissionStatus.GRANTED -> stringResource(R.string.notifications_permission_granted)
                PushPermissionStatus.DENIED -> stringResource(R.string.notifications_permission_denied)
                PushPermissionStatus.NOT_DETERMINED -> stringResource(R.string.notifications_permission_unknown)
            }

            NotificationCard(
                icon = Icons.Outlined.Security,
                title = stringResource(R.string.notifications_permission_title)
            ) {
                TextBodyMediumNeutral80(permissionText)
                Spacer2()
                if (state.permissionStatus != PushPermissionStatus.GRANTED) {
                    CardButton(
                        text = stringResource(R.string.notifications_request_permission),
                        onClick = permissionRequester.request,
                        enabled = !state.permissionLoading
                    )
                }
            }
        }

        // Token Card
        item {
            val tokenText = state.pushToken?.let {
                stringResource(R.string.notifications_token, it.take(30) + "...")
            } ?: stringResource(R.string.notifications_no_token)

            NotificationCard(
                icon = Icons.Outlined.Key,
                title = stringResource(R.string.notifications_token_title)
            ) {
                TextBodyMediumNeutral80(tokenText)
                Spacer2()
                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(R.string.notifications_refresh_token),
                        onClick = viewModel::refreshToken,
                        modifier = Modifier.weight(1f),
                        enabled = !state.tokenRefreshing
                    )
                    OutlinedButton(
                        text = stringResource(R.string.notifications_log_token),
                        onClick = viewModel::logToken,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Send Notifications Card
        item {
            NotificationCard(
                icon = Icons.Outlined.NotificationsActive,
                title = stringResource(R.string.notifications_send_title)
            ) {
                state.lastSentNotification?.let {
                    TextBodyMediumNeutral80(stringResource(R.string.notifications_last_sent, it))
                    Spacer2()
                }
                val reminderTitle = stringResource(R.string.notifications_reminder_title)
                val reminderMessage = stringResource(R.string.notifications_reminder_message)
                val promoTitle = stringResource(R.string.notifications_promo_title)
                val promoMessage = stringResource(R.string.notifications_promo_message)

                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(R.string.notifications_send_reminder),
                        onClick = { viewModel.sendReminderNotification(reminderTitle, reminderMessage) },
                        modifier = Modifier.weight(1f),
                        enabled = state.permissionStatus == PushPermissionStatus.GRANTED
                    )
                    OutlinedButton(
                        text = stringResource(R.string.notifications_send_promo),
                        onClick = { viewModel.sendPromoNotification(promoTitle, promoMessage) },
                        modifier = Modifier.weight(1f),
                        enabled = state.permissionStatus == PushPermissionStatus.GRANTED
                    )
                }
            }
        }

        // Received Notifications Card
        item {
            NotificationCard(
                icon = Icons.Outlined.Notifications,
                title = stringResource(R.string.notifications_cancel_title)
            ) {
                state.lastReceivedNotification?.let {
                    TextBodyMediumNeutral80(stringResource(R.string.notifications_last_received, it))
                    Spacer2()
                }
                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(R.string.notifications_open_settings),
                        onClick = viewModel::openNotificationSettings,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedButton(
                        text = stringResource(R.string.notifications_cancel_all),
                        onClick = viewModel::cancelAllNotifications,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    NotificationsNavEvents(router, viewModel.navEvent)
}

@Composable
private fun NotificationCard(
    icon: ImageVector,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    AppElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(space4)) {
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
private fun CardButton(
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

@Composable
private fun NotificationsNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>,
) {
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is NotificationsNavEvent.OpenSettings -> router.openNotificationSettings()
        }
    }
}
