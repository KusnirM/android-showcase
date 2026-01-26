package com.mk.androidshowcase.domain.useCase.notifications

import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.repository.PushPermissionStatus
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetPushPermissionStatusUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : UseCase<None, PushPermissionStatus>() {
    override suspend fun run(params: None): PushPermissionStatus =
        pushNotificationService.getPermissionStatus()
}
