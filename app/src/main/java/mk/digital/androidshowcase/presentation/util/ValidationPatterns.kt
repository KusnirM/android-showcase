package mk.digital.androidshowcase.presentation.util

object ValidationPatterns {
    const val MIN_PASSWORD_LENGTH = 8

    val EMAIL_REGEX = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )

    val PASSWORD_REGEX = Regex(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
    )

    fun isValidEmail(email: String): Boolean = EMAIL_REGEX.matches(email)

    fun isValidPassword(password: String): Boolean = PASSWORD_REGEX.matches(password)

    fun isPasswordLongEnough(password: String): Boolean = password.length >= MIN_PASSWORD_LENGTH
}
