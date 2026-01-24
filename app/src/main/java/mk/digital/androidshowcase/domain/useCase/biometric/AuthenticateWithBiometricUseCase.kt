package mk.digital.androidshowcase.domain.useCase.biometric

import mk.digital.androidshowcase.data.biometric.BiometricResult
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class AuthenticateWithBiometricUseCase @Inject constructor(
    private val biometricRepository: BiometricRepository
) : UseCase<None, BiometricResult>() {
    override suspend fun run(params: None): BiometricResult = biometricRepository.authenticate()
}
