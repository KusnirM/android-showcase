package mk.digital.androidshowcase.domain.useCase.analytics

import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class RecordExceptionUseCase @Inject constructor(
    private val analyticsClient: AnalyticsClient
) : UseCase<Throwable, Unit>() {
    override suspend fun run(params: Throwable) = analyticsClient.recordException(params)
}
