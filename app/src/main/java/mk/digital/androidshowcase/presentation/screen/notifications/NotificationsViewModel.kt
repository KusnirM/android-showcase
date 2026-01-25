package mk.digital.androidshowcase.presentation.screen.notifications

import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.model.NotificationChannel
import mk.digital.androidshowcase.domain.repository.PushPermissionStatus
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.notifications.CancelAllNotificationsUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.GetPushPermissionStatusUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.LogPushTokenUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.ObservePushNotificationsUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.ObservePushTokenUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.RefreshPushTokenUseCase
import mk.digital.androidshowcase.domain.useCase.notifications.ShowLocalNotificationUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.base.NavEvent
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getPushPermissionStatusUseCase: GetPushPermissionStatusUseCase,
    private val observePushTokenUseCase: ObservePushTokenUseCase,
    private val observePushNotificationsUseCase: ObservePushNotificationsUseCase,
    private val refreshPushTokenUseCase: RefreshPushTokenUseCase,
    private val logPushTokenUseCase: LogPushTokenUseCase,
    private val showLocalNotificationUseCase: ShowLocalNotificationUseCase,
    private val cancelAllNotificationsUseCase: CancelAllNotificationsUseCase,
) : BaseViewModel<NotificationsUiState>(NotificationsUiState()) {

    override fun loadInitialData() {
        execute(
            action = { getPushPermissionStatusUseCase() },
            onSuccess = { status -> newState { it.copy(permissionStatus = status) } }
        )

        observe(
            flow = observePushTokenUseCase(),
            onEach = { token -> newState { it.copy(pushToken = token) } }
        )

        observe(
            flow = observePushNotificationsUseCase(),
            onEach = { notification ->
                newState { it.copy(lastReceivedNotification = "${notification.title}: ${notification.message}") }
            }
        )
    }

    fun updatePermissionStatus(status: PushPermissionStatus) {
        newState { it.copy(permissionStatus = status) }
    }

    fun refreshToken() {
        execute(
            action = { refreshPushTokenUseCase() },
            onLoading = { newState { it.copy(tokenRefreshing = true) } },
            onSuccess = { newState { it.copy(tokenRefreshing = false) } },
            onError = { newState { it.copy(tokenRefreshing = false) } }
        )
    }

    fun logToken() {
        execute(action = { logPushTokenUseCase() })
    }

    @OptIn(ExperimentalUuidApi::class)
    fun sendReminderNotification(title: String, message: String) {
        val notification = Notification(
            id = Uuid.random().toString(),
            title = title,
            message = message,
            channel = NotificationChannel.REMINDERS
        )
        execute(
            action = { showLocalNotificationUseCase(notification) },
            onSuccess = { newState { it.copy(lastSentNotification = notification.title) } }
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    fun sendPromoNotification(title: String, message: String) {
        val notification = Notification(
            id = Uuid.random().toString(),
            title = title,
            message = message,
            channel = NotificationChannel.PROMOTIONS
        )
        execute(
            action = { showLocalNotificationUseCase(notification) },
            onSuccess = { newState { it.copy(lastSentNotification = notification.title) } }
        )
    }

    fun cancelAllNotifications() {
        execute(
            action = { cancelAllNotificationsUseCase() },
            onSuccess = { newState { it.copy(lastSentNotification = null) } }
        )
    }

    fun openNotificationSettings() {
        navigate(NotificationsNavEvent.OpenSettings)
    }
}

sealed interface NotificationsNavEvent : NavEvent {
    data object OpenSettings : NotificationsNavEvent
}

data class NotificationsUiState(
    val permissionStatus: PushPermissionStatus = PushPermissionStatus.NOT_DETERMINED,
    val permissionLoading: Boolean = false,
    val pushToken: String? = null,
    val tokenRefreshing: Boolean = false,
    val lastSentNotification: String? = null,
    val lastReceivedNotification: String? = null,
)
