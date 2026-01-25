package mk.digital.androidshowcase.domain.useCase.notifications

import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.useCase.base.BaseTest
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.base.test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ObservePushTokenUseCaseTest : BaseTest<ObservePushTokenUseCase>() {

    override lateinit var classUnderTest: ObservePushTokenUseCase

    @MockK
    private lateinit var pushNotificationService: PushNotificationService

    override fun beforeEach() {
        classUnderTest = ObservePushTokenUseCase(pushNotificationService)
    }

    @Test
    fun `invoke emits token from service`() = runTest {
        val expectedToken = "test-token-123"

        test(
            given = {
                every { pushNotificationService.token } returns MutableStateFlow(expectedToken)
            },
            whenAction = {
                classUnderTest().first()
            },
            then = {
                assertEquals(expectedToken, it)
            }
        )
    }
}
