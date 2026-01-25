package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.model.NotificationChannel
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.useCase.base.BaseTest
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.base.test
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
