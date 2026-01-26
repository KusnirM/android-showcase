package com.mk.androidshowcase.presentation.screen.login

import dagger.hilt.android.lifecycle.HiltViewModel
import com.mk.androidshowcase.data.biometric.BiometricResult
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.domain.useCase.biometric.AuthenticateWithBiometricUseCase
import com.mk.androidshowcase.domain.useCase.biometric.IsBiometricEnabledUseCase
import com.mk.androidshowcase.presentation.base.BaseViewModel
import com.mk.androidshowcase.presentation.base.NavEvent
import com.mk.androidshowcase.presentation.util.ValidationPatterns
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val isBiometricEnabledUseCase: IsBiometricEnabledUseCase,
    private val authenticateWithBiometricUseCase: AuthenticateWithBiometricUseCase,
) : BaseViewModel<LoginUiState>(LoginUiState()) {

    fun skip() = navigate(LoginNavEvent.ToHome)

    fun toRegister() = navigate(LoginNavEvent.ToRegister)

    override fun loadInitialData() {
        execute(
            action = { isBiometricEnabledUseCase() },
            onSuccess = { enabled -> newState { it.copy(biometricsAvailable = enabled) } }
        )
    }

    fun onEmailChange(email: String) = newState { it.copy(email = email, emailError = null) }

    fun onPasswordChange(password: String) = newState { it.copy(password = password, passwordError = null) }

    fun fillTestAccount() {
        newState {
            it.copy(
                email = TEST_EMAIL,
                password = TEST_PASSWORD,
                emailError = null,
                passwordError = null
            )
        }
    }

    fun login() {
        requireState { state ->
            val emailError = validateEmail(state.email)
            val passwordError = validatePassword(state.password)

            if (emailError != null || passwordError != null) {
                newState {
                    it.copy(
                        emailError = emailError,
                        passwordError = passwordError
                    )
                }
                return@requireState
            }

            // Login successful - navigate to home
            navigate(LoginNavEvent.ToHome)
        }
    }

    fun authenticateWithBiometrics() {
        execute(
            action = { authenticateWithBiometricUseCase() },
            onLoading = { newState { it.copy(biometricsLoading = true, biometricsResult = null) } },
            onSuccess = { result ->
                newState {
                    it.copy(
                        biometricsLoading = false,
                        biometricsResult = result
                    )
                }
                if (result is BiometricResult.Success) {
                    navigate(LoginNavEvent.ToHome)
                }
            },
            onError = { error ->
                newState {
                    it.copy(
                        biometricsLoading = false,
                        biometricsResult = BiometricResult.SystemError(error.message.orEmpty())
                    )
                }
            }
        )
    }

    private fun validateEmail(email: String): EmailError? {
        return when {
            email.isBlank() -> EmailError.EMPTY
            !ValidationPatterns.isValidEmail(email) -> EmailError.INVALID_FORMAT
            else -> null
        }
    }

    private fun validatePassword(password: String): PasswordError? {
        return when {
            password.isBlank() -> PasswordError.EMPTY
            !ValidationPatterns.isPasswordLongEnough(password) -> PasswordError.TOO_SHORT
            !ValidationPatterns.isValidPassword(password) -> PasswordError.WEAK
            else -> null
        }
    }

    companion object {
        const val TEST_EMAIL = "test@example.com"
        const val TEST_PASSWORD = "Test123!"
    }
}

enum class EmailError {
    EMPTY,
    INVALID_FORMAT
}

enum class PasswordError {
    EMPTY,
    TOO_SHORT,
    WEAK
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val biometricsAvailable: Boolean = false,
    val biometricsLoading: Boolean = false,
    val biometricsResult: BiometricResult? = null,
)

sealed interface LoginNavEvent : NavEvent {
    data object ToHome : LoginNavEvent
    data object ToRegister : LoginNavEvent
}
