package mk.digital.androidshowcase.domain.useCase.notifications

import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class ShowLocalNotificationUseCase @Inject constructor(
    private val localNotificationService: LocalNotificationService
) : UseCase<Notification, Unit>() {
    override suspend fun run(params: Notification) =
        localNotificationService.showNotification(params)
}
