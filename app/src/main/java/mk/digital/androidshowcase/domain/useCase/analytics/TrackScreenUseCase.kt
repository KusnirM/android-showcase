package mk.digital.androidshowcase.domain.useCase.analytics

import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import javax.inject.Inject

class TrackScreenUseCase @Inject constructor(
    private val analyticsClient: AnalyticsClient
) {
    operator fun invoke(screenName: String) {
        analyticsClient.trackScreen(screenName)
    }
}
