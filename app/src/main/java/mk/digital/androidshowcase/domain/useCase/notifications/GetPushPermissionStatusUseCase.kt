package mk.digital.androidshowcase.domain.useCase.notifications

import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.repository.PushPermissionStatus
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetPushPermissionStatusUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : UseCase<None, PushPermissionStatus>() {
    override suspend fun run(params: None): PushPermissionStatus =
        pushNotificationService.getPermissionStatus()
}
