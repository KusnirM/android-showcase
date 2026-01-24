package mk.digital.androidshowcase.domain.useCase

import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class TrackButtonClickUseCase @Inject constructor(
    private val analyticsClient: AnalyticsClient
) : UseCase<Int, Unit>() {
    override suspend fun run(params: Int) {
        analyticsClient.log("Button Clicked: $params")
    }
}
