package com.mk.androidshowcase.domain.repository

import com.mk.androidshowcase.data.biometric.BiometricResult

interface BiometricRepository {
    fun enabled(): Boolean
    suspend fun authenticate(): BiometricResult
}
