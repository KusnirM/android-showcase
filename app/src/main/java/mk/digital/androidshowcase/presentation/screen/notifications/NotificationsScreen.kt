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
import androidx.compose.material.icons.outlined.Cancel
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mk.digital.androidshowcase.domain.repository.PushPermissionStatus
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.component.buttons.OutlinedButton
import mk.digital.androidshowcase.presentation.component.permission.rememberNotificationPermissionRequester
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.notifications_cancel_all
import mk.digital.androidshowcase.shared.generated.resources.notifications_cancel_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_open_settings
import mk.digital.androidshowcase.shared.generated.resources.notifications_last_received
import mk.digital.androidshowcase.shared.generated.resources.notifications_last_sent
import mk.digital.androidshowcase.shared.generated.resources.notifications_log_token
import mk.digital.androidshowcase.shared.generated.resources.notifications_no_token
import mk.digital.androidshowcase.shared.generated.resources.notifications_permission_denied
import mk.digital.androidshowcase.shared.generated.resources.notifications_permission_granted
import mk.digital.androidshowcase.shared.generated.resources.notifications_permission_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_permission_unknown
import mk.digital.androidshowcase.shared.generated.resources.notifications_refresh_token
import mk.digital.androidshowcase.shared.generated.resources.notifications_request_permission
import mk.digital.androidshowcase.shared.generated.resources.notifications_promo_message
import mk.digital.androidshowcase.shared.generated.resources.notifications_promo_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_reminder_message
import mk.digital.androidshowcase.shared.generated.resources.notifications_reminder_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_send_promo
import mk.digital.androidshowcase.shared.generated.resources.notifications_send_reminder
import mk.digital.androidshowcase.shared.generated.resources.notifications_send_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_subtitle
import mk.digital.androidshowcase.shared.generated.resources.notifications_title
import mk.digital.androidshowcase.shared.generated.resources.notifications_token
import mk.digital.androidshowcase.shared.generated.resources.notifications_token_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel) {
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
                TextHeadlineMediumPrimary(stringResource(Res.string.notifications_title))
                TextBodyMediumNeutral80(stringResource(Res.string.notifications_subtitle))
            }
        }

        // Permission Card
        item {
            val permissionText = when (state.permissionStatus) {
                PushPermissionStatus.GRANTED -> stringResource(Res.string.notifications_permission_granted)
                PushPermissionStatus.DENIED -> stringResource(Res.string.notifications_permission_denied)
                PushPermissionStatus.NOT_DETERMINED -> stringResource(Res.string.notifications_permission_unknown)
            }

            NotificationCard(
                icon = Icons.Outlined.Security,
                title = stringResource(Res.string.notifications_permission_title)
            ) {
                TextBodyMediumNeutral80(permissionText)
                Spacer2()
                if (state.permissionStatus != PushPermissionStatus.GRANTED) {
                    CardButton(
                        text = stringResource(Res.string.notifications_request_permission),
                        onClick = permissionRequester.request,
                        enabled = !state.permissionLoading
                    )
                }
            }
        }

        // Token Card
        item {
            val tokenText = state.pushToken?.let {
                stringResource(Res.string.notifications_token, it.take(30) + "...")
            } ?: stringResource(Res.string.notifications_no_token)

            NotificationCard(
                icon = Icons.Outlined.Key,
                title = stringResource(Res.string.notifications_token_title)
            ) {
                TextBodyMediumNeutral80(tokenText)
                Spacer2()
                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_refresh_token),
                        onClick = viewModel::refreshToken,
                        modifier = Modifier.weight(1f),
                        enabled = !state.tokenRefreshing
                    )
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_log_token),
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
                title = stringResource(Res.string.notifications_send_title)
            ) {
                state.lastSentNotification?.let {
                    TextBodyMediumNeutral80(stringResource(Res.string.notifications_last_sent, it))
                    Spacer2()
                }
                val reminderTitle = stringResource(Res.string.notifications_reminder_title)
                val reminderMessage = stringResource(Res.string.notifications_reminder_message)
                val promoTitle = stringResource(Res.string.notifications_promo_title)
                val promoMessage = stringResource(Res.string.notifications_promo_message)

                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_send_reminder),
                        onClick = { viewModel.sendReminderNotification(reminderTitle, reminderMessage) },
                        modifier = Modifier.weight(1f),
                        enabled = state.permissionStatus == PushPermissionStatus.GRANTED
                    )
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_send_promo),
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
                title = stringResource(Res.string.notifications_cancel_title)
            ) {
                state.lastReceivedNotification?.let {
                    TextBodyMediumNeutral80(stringResource(Res.string.notifications_last_received, it))
                    Spacer2()
                }
                Row(horizontalArrangement = Arrangement.spacedBy(space4)) {
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_open_settings),
                        onClick = viewModel::openNotificationSettings,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedButton(
                        text = stringResource(Res.string.notifications_cancel_all),
                        onClick = viewModel::cancelAllNotifications,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(
    icon: ImageVector,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    AppElevatedCard(modifier = Modifier.fillMaxWidth().padding(space4)) {
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
fun NotificationsNavEvents(
    viewModel: NotificationsViewModel,
    router: NavRouter<Route>,
) {
    CollectNavEvents(navEventFlow = viewModel.navEvent) { event ->
        if (event !is NotificationsNavEvent) return@CollectNavEvents
        when (event) {
            is NotificationsNavEvent.OpenSettings -> {
                router.openNotificationSettings()
            }
        }
    }
}
