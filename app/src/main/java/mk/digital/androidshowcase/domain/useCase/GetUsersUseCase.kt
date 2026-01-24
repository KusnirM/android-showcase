package mk.digital.androidshowcase.domain.useCase

import mk.digital.androidshowcase.domain.model.User
import mk.digital.androidshowcase.domain.repository.UserRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<None, List<User>>() {
    override suspend fun run(params: None): List<User> = userRepository.getUsers()
}
