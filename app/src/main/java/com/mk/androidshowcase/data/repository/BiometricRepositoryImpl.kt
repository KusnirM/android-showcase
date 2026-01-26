package com.mk.androidshowcase.data.repository

import com.mk.androidshowcase.data.biometric.BiometricClient
import com.mk.androidshowcase.data.biometric.BiometricResult
import com.mk.androidshowcase.domain.repository.BiometricRepository
import javax.inject.Inject

class BiometricRepositoryImpl @Inject constructor(
    private val biometricClient: BiometricClient,
) : BiometricRepository {

    override fun enabled(): Boolean = biometricClient.enabled()

    override suspend fun authenticate(): BiometricResult = biometricClient.authenticate()
}
