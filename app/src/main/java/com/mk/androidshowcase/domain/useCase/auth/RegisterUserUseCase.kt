package com.mk.androidshowcase.domain.useCase.auth

import com.mk.androidshowcase.domain.model.RegisteredUser
import com.mk.androidshowcase.domain.repository.AuthRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
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
