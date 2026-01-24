package mk.digital.androidshowcase.domain.repository

import mk.digital.androidshowcase.data.biometric.BiometricResult

interface BiometricRepository {
    fun enabled(): Boolean
    suspend fun authenticate(): BiometricResult
}
