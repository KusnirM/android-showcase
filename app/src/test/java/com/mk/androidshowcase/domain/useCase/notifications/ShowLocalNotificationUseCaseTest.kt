package com.mk.androidshowcase.domain.useCase.notifications

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.mk.androidshowcase.domain.model.Notification
import com.mk.androidshowcase.domain.model.NotificationChannel
import com.mk.androidshowcase.domain.repository.LocalNotificationService
import com.mk.androidshowcase.base.BaseTest
import com.mk.androidshowcase.base.test
import org.junit.jupiter.api.Test

class ShowLocalNotificationUseCaseTest : BaseTest<ShowLocalNotificationUseCase>() {

    override lateinit var classUnderTest: ShowLocalNotificationUseCase

    @MockK
    private lateinit var localNotificationService: LocalNotificationService

    override fun beforeEach() {
        classUnderTest = ShowLocalNotificationUseCase(localNotificationService)
    }

    @Test
    fun `invoke calls showNotification on service with correct notification`() = runTest {
        val notification = Notification(
            id = "test-id",
            title = "Test Title",
            message = "Test Message",
            channel = NotificationChannel.REMINDERS
        )

        test(
            given = {
                coJustRun { localNotificationService.showNotification(notification) }
            },
            whenAction = {
                classUnderTest(notification)
            },
            then = {
                coVerify(exactly = 1) { localNotificationService.showNotification(notification) }
            }
        )
    }
}
