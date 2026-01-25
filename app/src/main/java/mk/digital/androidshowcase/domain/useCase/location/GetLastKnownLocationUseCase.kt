package mk.digital.androidshowcase.domain.useCase.location

import mk.digital.androidshowcase.domain.model.Location
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetLastKnownLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : UseCase<None, Location>() {
    override suspend fun run(params: None): Location = locationRepository.lastKnownLocation()
}
