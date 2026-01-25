package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.model.Notification
import mk.digital.androidshowcase.domain.model.NotificationChannel
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.base.BaseTest
import mk.digital.androidshowcase.base.test
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
