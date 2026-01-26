package com.mk.androidshowcase.domain.useCase.notifications

import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class LogPushTokenUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = pushNotificationService.logToken()
}
