package mk.digital.androidshowcase.data.repository.user

import mk.digital.androidshowcase.data.base.transformAll
import mk.digital.androidshowcase.domain.model.User
import mk.digital.androidshowcase.domain.repository.UserRepository

class UserRepositoryImpl(
    private val client: UserClient
) : UserRepository {
    override suspend fun getUser(id: Int): User {
        return client.fetchUser(id).transform()
    }

    override suspend fun getUsers(): List<User> {
        return client.fetchUsers().transformAll()
    }
}
