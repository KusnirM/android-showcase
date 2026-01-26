package com.mk.androidshowcase.domain.useCase.analytics

import com.mk.androidshowcase.data.analytics.AnalyticsClient
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class RecordExceptionUseCase @Inject constructor(
    private val analyticsClient: AnalyticsClient
) : UseCase<Throwable, Unit>() {
    override suspend fun run(params: Throwable) = analyticsClient.recordException(params)
}
