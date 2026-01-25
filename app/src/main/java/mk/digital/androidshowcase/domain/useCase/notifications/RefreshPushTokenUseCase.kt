package mk.digital.androidshowcase.domain.useCase.notifications

import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class RefreshPushTokenUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = pushNotificationService.refreshToken()
}
