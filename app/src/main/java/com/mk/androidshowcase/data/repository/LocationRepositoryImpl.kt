package com.mk.androidshowcase.data.repository

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.data.location.LocationClient
import com.mk.androidshowcase.domain.exceptions.base.LocationErrorCode
import com.mk.androidshowcase.domain.exceptions.base.LocationException
import com.mk.androidshowcase.domain.model.Location
import com.mk.androidshowcase.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationClient: LocationClient
) : LocationRepository {

    override suspend fun lastKnownLocation(): Location {
        return locationClient.lastKnown() ?: throw LocationException(
            message = "Last known location not available",
            userMessage = "Location not available. Please enable location services.",
            errorCode = LocationErrorCode.NOT_AVAILABLE
        )
    }

    override fun locationUpdates(highAccuracy: Boolean): Flow<Location> {
        return locationClient.updates(highAccuracy)
    }
}
