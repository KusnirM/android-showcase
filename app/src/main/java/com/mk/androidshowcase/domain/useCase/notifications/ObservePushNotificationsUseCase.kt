package com.mk.androidshowcase.domain.useCase.notifications

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.model.Notification
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.useCase.base.FlowUseCase
import com.mk.androidshowcase.domain.useCase.base.None
import javax.inject.Inject

class ObservePushNotificationsUseCase @Inject constructor(
    private val pushNotificationService: PushNotificationService
) : FlowUseCase<None, Notification>() {
    override fun run(params: None): Flow<Notification> = pushNotificationService.notifications
}
