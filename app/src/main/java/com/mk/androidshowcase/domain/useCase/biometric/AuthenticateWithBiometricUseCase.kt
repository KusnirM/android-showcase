package com.mk.androidshowcase.domain.useCase.biometric

import com.mk.androidshowcase.data.biometric.BiometricResult
import com.mk.androidshowcase.domain.repository.BiometricRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class AuthenticateWithBiometricUseCase @Inject constructor(
    private val biometricRepository: BiometricRepository
) : UseCase<None, BiometricResult>() {
    override suspend fun run(params: None): BiometricResult = biometricRepository.authenticate()
}
