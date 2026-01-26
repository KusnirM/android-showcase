package com.mk.androidshowcase.domain.useCase.biometric

import com.mk.androidshowcase.domain.repository.BiometricRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class IsBiometricEnabledUseCase @Inject constructor(
    private val biometricRepository: BiometricRepository
) : UseCase<None, Boolean>() {
    override suspend fun run(params: None): Boolean = biometricRepository.enabled()
}
