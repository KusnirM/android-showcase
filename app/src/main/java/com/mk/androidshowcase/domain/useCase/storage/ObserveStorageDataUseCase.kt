package com.mk.androidshowcase.domain.useCase.storage

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.model.StorageData
import com.mk.androidshowcase.domain.repository.StorageRepository
import com.mk.androidshowcase.domain.useCase.base.FlowUseCase
import com.mk.androidshowcase.domain.useCase.base.None
import javax.inject.Inject

class ObserveStorageDataUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) : FlowUseCase<None, StorageData>() {
    override fun run(params: None): Flow<StorageData> = storageRepository.storageData
}
