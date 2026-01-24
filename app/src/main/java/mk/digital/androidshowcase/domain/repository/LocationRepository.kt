package mk.digital.androidshowcase.domain.repository

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.domain.model.Location

interface LocationRepository {
    suspend fun lastKnownLocation(): Location
    fun locationUpdates(highAccuracy: Boolean = false): Flow<Location>
}
