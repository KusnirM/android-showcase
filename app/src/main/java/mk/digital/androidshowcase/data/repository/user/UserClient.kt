package mk.digital.androidshowcase.data.repository.user

import mk.digital.androidshowcase.data.dto.UserDTO
import mk.digital.androidshowcase.data.network.UserApi
import mk.digital.androidshowcase.data.network.handleApiCall
import javax.inject.Inject

interface UserClient {
    suspend fun fetchUser(id: Int): UserDTO
    suspend fun fetchUsers(): List<UserDTO>
}

class UserClientImpl @Inject constructor(
    private val userApi: UserApi
) : UserClient {

    override suspend fun fetchUser(id: Int): UserDTO = handleApiCall {
        userApi.fetchUser(id)
    }

    override suspend fun fetchUsers(): List<UserDTO> = handleApiCall {
        userApi.fetchUsers()
    }
}
