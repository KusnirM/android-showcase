package com.mk.androidshowcase.domain.useCase.analytics

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import com.mk.androidshowcase.data.analytics.AnalyticsClient
import com.mk.androidshowcase.base.BaseTest
import com.mk.androidshowcase.base.test
import org.junit.jupiter.api.Test

class RecordExceptionUseCaseTest : BaseTest<RecordExceptionUseCase>() {

    override lateinit var classUnderTest: RecordExceptionUseCase

    @MockK
    private lateinit var analyticsClient: AnalyticsClient

    override fun beforeEach() {
        classUnderTest = RecordExceptionUseCase(analyticsClient)
    }

    @Test
    fun `invoke calls recordException on analytics client`() = runTest {
        val exception = RuntimeException("Test exception")

        test(
            given = {
                coJustRun { analyticsClient.recordException(exception) }
            },
            whenAction = {
                classUnderTest(exception)
            },
            then = {
                coVerify(exactly = 1) { analyticsClient.recordException(exception) }
            }
        )
    }
}
