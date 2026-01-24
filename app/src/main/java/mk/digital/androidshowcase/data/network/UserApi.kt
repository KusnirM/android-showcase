package mk.digital.androidshowcase.data.network

import mk.digital.androidshowcase.data.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun fetchUser(@Path("id") id: Int): UserDTO

    @GET("users")
    suspend fun fetchUsers(): List<UserDTO>
}
