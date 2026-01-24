package mk.digital.androidshowcase.domain.useCase.storage

import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class SetSessionCounterUseCase(
    private val storageRepository: StorageRepository
) : UseCase<Int, Unit>() {
    override suspend fun run(params: Int) = storageRepository.setSessionCounter(params)
}
