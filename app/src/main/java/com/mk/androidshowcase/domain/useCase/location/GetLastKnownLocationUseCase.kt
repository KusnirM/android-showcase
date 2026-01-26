package com.mk.androidshowcase.domain.useCase.location

import com.mk.androidshowcase.domain.model.Location
import com.mk.androidshowcase.domain.repository.LocationRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetLastKnownLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : UseCase<None, Location>() {
    override suspend fun run(params: None): Location = locationRepository.lastKnownLocation()
}
