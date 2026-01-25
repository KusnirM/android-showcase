package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.base.BaseTest
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.base.test
import org.junit.jupiter.api.Test

class LogPushTokenUseCaseTest : BaseTest<LogPushTokenUseCase>() {

    override lateinit var classUnderTest: LogPushTokenUseCase

    @MockK
    private lateinit var pushNotificationService: PushNotificationService

    override fun beforeEach() {
        classUnderTest = LogPushTokenUseCase(pushNotificationService)
    }

    @Test
    fun `invoke calls logToken on service`() = runTest {
        test(
            given = {
                coJustRun { pushNotificationService.logToken() }
            },
            whenAction = {
                classUnderTest()
            },
            then = {
                coVerify(exactly = 1) { pushNotificationService.logToken() }
            }
        )
    }
}
