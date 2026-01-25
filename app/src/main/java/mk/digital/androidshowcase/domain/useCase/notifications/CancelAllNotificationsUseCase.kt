package mk.digital.androidshowcase.domain.useCase.notifications

import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class CancelAllNotificationsUseCase @Inject constructor(
    private val localNotificationService: LocalNotificationService
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = localNotificationService.cancelAllNotifications()
}
