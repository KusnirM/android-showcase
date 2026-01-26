package com.mk.androidshowcase.domain.useCase.notifications

import com.mk.androidshowcase.domain.model.Notification
import com.mk.androidshowcase.domain.repository.LocalNotificationService
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class ShowLocalNotificationUseCase @Inject constructor(
    private val localNotificationService: LocalNotificationService
) : UseCase<Notification, Unit>() {
    override suspend fun run(params: Notification) =
        localNotificationService.showNotification(params)
}
