package mk.digital.androidshowcase.domain.useCase.notifications

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.useCase.base.FlowUseCase
import mk.digital.androidshowcase.domain.useCase.base.None
import javax.inject.Inject

class ObservePushTokenUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : FlowUseCase<None, String?>() {
    override fun run(params: None): Flow<String?> = pushNotificationService.token
}
