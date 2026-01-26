package com.mk.androidshowcase.domain.repository

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.model.Location

interface LocationRepository {
    suspend fun lastKnownLocation(): Location
    fun locationUpdates(highAccuracy: Boolean = false): Flow<Location>
}
