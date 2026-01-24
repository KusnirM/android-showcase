package mk.digital.androidshowcase.domain.useCase.analytics

import mk.digital.androidshowcase.data.analytics.AnalyticsClient

class TrackScreenUseCase(
    private val analyticsClient: AnalyticsClient
) {
    operator fun invoke(screenName: String) {
        analyticsClient.trackScreen(screenName)
    }
}
