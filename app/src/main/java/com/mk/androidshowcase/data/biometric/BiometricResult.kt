package com.mk.androidshowcase.data.biometric

sealed interface BiometricResult {
    data object Success : BiometricResult
    data object Cancelled : BiometricResult
    data object NotAvailable : BiometricResult
    data object ActivityNotAvailable : BiometricResult
    data class SystemError(val message: String) : BiometricResult
}
