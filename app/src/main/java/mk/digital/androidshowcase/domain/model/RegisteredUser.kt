package mk.digital.androidshowcase.domain.model

data class RegisteredUser(
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: Long,
)
