package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.domain.useCase.base.BaseTest
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.base.test
import org.junit.jupiter.api.Test

class CancelAllNotificationsUseCaseTest : BaseTest<CancelAllNotificationsUseCase>() {

    override lateinit var classUnderTest: CancelAllNotificationsUseCase

    @MockK
    private lateinit var localNotificationService: LocalNotificationService

    override fun beforeEach() {
        classUnderTest = CancelAllNotificationsUseCase(localNotificationService)
    }

    @Test
    fun `invoke calls cancelAllNotifications on service`() = runTest {
        test(
            given = {
                coJustRun { localNotificationService.cancelAllNotifications() }
            },
            whenAction = {
                classUnderTest()
            },
            then = {
                coVerify(exactly = 1) { localNotificationService.cancelAllNotifications() }
            }
        )
    }
}
