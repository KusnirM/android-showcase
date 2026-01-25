package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.useCase.base.BaseTest
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.base.test
import org.junit.jupiter.api.Test

class RefreshPushTokenUseCaseTest : BaseTest<RefreshPushTokenUseCase>() {

    override lateinit var classUnderTest: RefreshPushTokenUseCase

    @MockK
    private lateinit var pushNotificationService: PushNotificationService

    override fun beforeEach() {
        classUnderTest = RefreshPushTokenUseCase(pushNotificationService)
    }

    @Test
    fun `invoke calls refreshToken on service`() = runTest {
        test(
            given = {
                coJustRun { pushNotificationService.refreshToken() }
            },
            whenAction = {
                classUnderTest()
            },
            then = {
                coVerify(exactly = 1) { pushNotificationService.refreshToken() }
            }
        )
    }
}
