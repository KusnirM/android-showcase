package mk.digital.androidshowcase.data.repository

import mk.digital.androidshowcase.data.biometric.BiometricClient
import mk.digital.androidshowcase.data.biometric.BiometricResult
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import javax.inject.Inject

class BiometricRepositoryImpl @Inject constructor(
    private val biometricClient: BiometricClient,
) : BiometricRepository {

    override fun enabled(): Boolean = biometricClient.enabled()

    override suspend fun authenticate(): BiometricResult = biometricClient.authenticate()
}
