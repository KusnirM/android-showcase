package mk.digital.androidshowcase.domain.useCase.auth

import mk.digital.androidshowcase.domain.repository.AuthRepository
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class CheckEmailExistsUseCase(
    private val authRepository: AuthRepository
) : UseCase<String, Boolean>() {
    override suspend fun run(params: String): Boolean = authRepository.emailExists(params)
}
