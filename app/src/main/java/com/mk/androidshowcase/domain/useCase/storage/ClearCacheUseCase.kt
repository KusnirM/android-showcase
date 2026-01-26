package com.mk.androidshowcase.domain.useCase.storage

import com.mk.androidshowcase.domain.repository.StorageRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class ClearCacheUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = storageRepository.clear()
}
