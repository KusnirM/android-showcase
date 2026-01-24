package mk.digital.androidshowcase.presentation.screen.register

import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.domain.exceptions.base.BaseException
import mk.digital.androidshowcase.domain.useCase.auth.CheckEmailExistsUseCase
import mk.digital.androidshowcase.domain.useCase.auth.RegisterUserUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.util.ValidationPatterns
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val checkEmailExistsUseCase: CheckEmailExistsUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : BaseViewModel<RegisterUiState>(RegisterUiState()) {

    fun onNameChange(name: String) {
        newState { it.copy(name = name, nameError = null) }
    }

    fun onEmailChange(email: String) {
        newState { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        newState { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        newState { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun register() {
        requireState { state ->
            val nameError = validateName(state.name)
            val emailError = validateEmail(state.email)
            val passwordError = validatePassword(state.password)
            val confirmPasswordError = validateConfirmPassword(state.password, state.confirmPassword)

            if (nameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
                newState {
                    it.copy(
                        nameError = nameError,
                        emailError = emailError,
                        passwordError = passwordError,
                        confirmPasswordError = confirmPasswordError
                    )
                }
                return@requireState
            }

            performRegistration(state.name, state.email, state.password)
        }
    }

    private fun performRegistration(name: String, email: String, password: String) {
        execute(
            action = {
                val emailExists = checkEmailExistsUseCase(email)
                if (emailExists) throw EmailAlreadyExistsException()

                registerUserUseCase(RegisterUserUseCase.Params(name, email, password))
            },
            onLoading = { newState { it.copy(isLoading = true) } },
            onSuccess = {
                newState { it.copy(isLoading = false) }
                navigate(RegisterNavEvent.ToHome)
            },
            onError = { error: BaseException ->
                newState {
                    it.copy(
                        isLoading = false,
                        emailError = if (error is EmailAlreadyExistsException) {
                            RegisterEmailError.ALREADY_EXISTS
                        } else null
                    )
                }
            }
        )
    }

    fun toLogin() {
        navigate(RegisterNavEvent.ToLogin)
    }

    private fun validateName(name: String): RegisterNameError? {
        return when {
            name.isBlank() -> RegisterNameError.EMPTY
            name.length < MIN_NAME_LENGTH -> RegisterNameError.TOO_SHORT
            else -> null
        }
    }

    private fun validateEmail(email: String): RegisterEmailError? {
        return when {
            email.isBlank() -> RegisterEmailError.EMPTY
            !ValidationPatterns.isValidEmail(email) -> RegisterEmailError.INVALID_FORMAT
            else -> null
        }
    }

    private fun validatePassword(password: String): RegisterPasswordError? {
        return when {
            password.isBlank() -> RegisterPasswordError.EMPTY
            !ValidationPatterns.isPasswordLongEnough(password) -> RegisterPasswordError.TOO_SHORT
            !ValidationPatterns.isValidPassword(password) -> RegisterPasswordError.WEAK
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): RegisterConfirmPasswordError? {
        return when {
            confirmPassword.isBlank() -> RegisterConfirmPasswordError.EMPTY
            confirmPassword != password -> RegisterConfirmPasswordError.MISMATCH
            else -> null
        }
    }

    companion object {
        private const val MIN_NAME_LENGTH = 2
    }
}

enum class RegisterNameError {
    EMPTY,
    TOO_SHORT
}

enum class RegisterEmailError {
    EMPTY,
    INVALID_FORMAT,
    ALREADY_EXISTS
}

enum class RegisterPasswordError {
    EMPTY,
    TOO_SHORT,
    WEAK
}

enum class RegisterConfirmPasswordError {
    EMPTY,
    MISMATCH
}

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: RegisterNameError? = null,
    val emailError: RegisterEmailError? = null,
    val passwordError: RegisterPasswordError? = null,
    val confirmPasswordError: RegisterConfirmPasswordError? = null,
    val isLoading: Boolean = false,
)

sealed interface RegisterNavEvent : NavEvent {
    data object ToHome : RegisterNavEvent
    data object ToLogin : RegisterNavEvent
}

private class EmailAlreadyExistsException : BaseException(
    message = "Email already exists",
    cause = null
) {
    override val errorCode: String = "5001"
    override val userMessage: String = "This email is already registered"
    override val shouldReport: Boolean = false
}
