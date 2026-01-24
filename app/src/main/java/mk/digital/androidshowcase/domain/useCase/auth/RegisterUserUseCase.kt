package mk.digital.androidshowcase.domain.useCase.auth

import mk.digital.androidshowcase.domain.model.RegisteredUser
import mk.digital.androidshowcase.domain.repository.AuthRepository
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class RegisterUserUseCase(
    private val authRepository: AuthRepository
) : UseCase<RegisterUserUseCase.Params, RegisteredUser>() {

    data class Params(
        val name: String,
        val email: String,
        val password: String
    )

    override suspend fun run(params: Params): RegisteredUser =
        authRepository.register(params.name, params.email, params.password)
}
