package com.mk.androidshowcase.domain.useCase.notifications

import com.mk.androidshowcase.domain.repository.LocalNotificationService
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class CancelAllNotificationsUseCase @Inject constructor(
    private val localNotificationService: LocalNotificationService
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = localNotificationService.cancelAllNotifications()
}
