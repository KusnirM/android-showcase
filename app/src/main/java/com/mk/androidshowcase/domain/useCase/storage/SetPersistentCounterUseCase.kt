package com.mk.androidshowcase.domain.useCase.storage

import com.mk.androidshowcase.domain.repository.StorageRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class SetPersistentCounterUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) : UseCase<Int, Unit>() {
    override suspend fun run(params: Int) = storageRepository.setPersistentCounter(params)
}
