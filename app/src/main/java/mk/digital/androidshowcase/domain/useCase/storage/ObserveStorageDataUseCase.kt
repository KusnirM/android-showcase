package mk.digital.androidshowcase.domain.useCase.storage

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.domain.model.StorageData
import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.useCase.base.FlowUseCase
import mk.digital.androidshowcase.domain.useCase.base.None

class ObserveStorageDataUseCase(
    private val storageRepository: StorageRepository
) : FlowUseCase<None, StorageData>() {
    override fun run(params: None): Flow<StorageData> = storageRepository.storageData
}
