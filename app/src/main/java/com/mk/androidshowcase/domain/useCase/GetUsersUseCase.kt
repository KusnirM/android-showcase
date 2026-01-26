package com.mk.androidshowcase.domain.useCase

import com.mk.androidshowcase.domain.model.User
import com.mk.androidshowcase.domain.repository.UserRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<None, List<User>>() {
    override suspend fun run(params: None): List<User> = userRepository.getUsers()
}
