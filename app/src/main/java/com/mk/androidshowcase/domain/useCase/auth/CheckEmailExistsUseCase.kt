package com.mk.androidshowcase.domain.useCase.auth

import com.mk.androidshowcase.domain.repository.AuthRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class CheckEmailExistsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<String, Boolean>() {
    override suspend fun run(params: String): Boolean = authRepository.emailExists(params)
}
