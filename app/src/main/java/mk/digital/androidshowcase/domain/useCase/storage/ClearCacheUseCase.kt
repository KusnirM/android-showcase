package mk.digital.androidshowcase.domain.useCase.storage

import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class ClearCacheUseCase(
    private val storageRepository: StorageRepository
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = storageRepository.clear()
}
