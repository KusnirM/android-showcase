package mk.digital.androidshowcase.domain.useCase.biometric

import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class IsBiometricEnabledUseCase(
    private val biometricRepository: BiometricRepository
) : UseCase<None, Boolean>() {
    override suspend fun run(params: None): Boolean = biometricRepository.enabled()
}
