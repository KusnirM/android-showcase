package mk.digital.androidshowcase.domain.repository

import mk.digital.androidshowcase.domain.model.User

interface UserRepository {

    suspend fun getUser(id: Int): User

    suspend fun getUsers(): List<User>

}
