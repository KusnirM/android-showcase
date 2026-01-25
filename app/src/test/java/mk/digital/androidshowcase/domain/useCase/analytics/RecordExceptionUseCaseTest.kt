package mk.digital.androidshowcase.domain.useCase.analytics

import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import mk.digital.androidshowcase.base.BaseTest
import mk.digital.androidshowcase.base.test
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
