package mk.digital.androidshowcase.domain.useCase.location

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.domain.model.Location
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.domain.useCase.base.FlowUseCase
import javax.inject.Inject

class ObserveLocationUpdatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : FlowUseCase<ObserveLocationUpdatesUseCase.Params, Location>() {

    override fun run(params: Params): Flow<Location> =
        locationRepository.locationUpdates(params.highAccuracy)

    data class Params(val highAccuracy: Boolean = false)
}
