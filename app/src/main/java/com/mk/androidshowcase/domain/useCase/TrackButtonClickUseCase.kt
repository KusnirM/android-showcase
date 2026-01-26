package com.mk.androidshowcase.domain.useCase

import com.mk.androidshowcase.data.analytics.AnalyticsClient
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class TrackButtonClickUseCase @Inject constructor(
    private val analyticsClient: AnalyticsClient
) : UseCase<Int, Unit>() {
    override suspend fun run(params: Int) {
        analyticsClient.log("Button Clicked: $params")
    }
}
