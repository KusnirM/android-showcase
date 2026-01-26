package com.mk.androidshowcase.domain.useCase.notifications

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.useCase.base.FlowUseCase
import com.mk.androidshowcase.domain.useCase.base.None
import javax.inject.Inject

class ObservePushTokenUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : FlowUseCase<None, String?>() {
    override fun run(params: None): Flow<String?> = pushNotificationService.token
}
