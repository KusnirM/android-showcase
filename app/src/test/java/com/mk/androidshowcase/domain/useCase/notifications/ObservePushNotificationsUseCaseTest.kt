package com.mk.androidshowcase.domain.useCase.notifications

import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import com.mk.androidshowcase.domain.model.Notification
import com.mk.androidshowcase.domain.model.NotificationChannel
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.base.BaseTest
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.base.test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ObservePushNotificationsUseCaseTest : BaseTest<ObservePushNotificationsUseCase>() {

    override lateinit var classUnderTest: ObservePushNotificationsUseCase

    @MockK
    private lateinit var pushNotificationService: PushNotificationService

    override fun beforeEach() {
        classUnderTest = ObservePushNotificationsUseCase(pushNotificationService)
    }

    @Test
    fun `invoke emits notifications from service`() = runTest {
        val expectedNotification = Notification(
            id = "test-id",
            title = "Test Title",
            message = "Test Message",
            channel = NotificationChannel.REMINDERS
        )

        test(
            given = {
                every { pushNotificationService.notifications } returns flowOf(expectedNotification)
            },
            whenAction = {
                classUnderTest().first()
            },
            then = {
                assertEquals(expectedNotification, it)
            }
        )
    }
}
