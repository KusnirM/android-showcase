package mk.digital.androidshowcase.data.repository.user

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import mk.digital.androidshowcase.data.dto.UserDTO
import mk.digital.androidshowcase.data.network.handleApiCall

interface UserClient {
    suspend fun fetchUser(id: Int): UserDTO
    suspend fun fetchUsers(): List<UserDTO>
}

class UserClientImpl(
    private val client: HttpClient
) : UserClient {

    override suspend fun fetchUser(id: Int): UserDTO = handleApiCall {
        client.get("users/$id").body()
    }

    override suspend fun fetchUsers(): List<UserDTO> = handleApiCall {
        client.get("users").body()
    }
}
