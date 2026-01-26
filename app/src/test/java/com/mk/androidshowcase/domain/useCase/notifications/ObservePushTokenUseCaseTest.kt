package com.mk.androidshowcase.domain.useCase.notifications

import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.base.BaseTest
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.base.test
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
