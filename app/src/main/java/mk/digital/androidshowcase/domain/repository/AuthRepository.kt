package mk.digital.androidshowcase.domain.repository

import mk.digital.androidshowcase.domain.model.RegisteredUser

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): RegisteredUser
    suspend fun login(email: String, password: String): RegisteredUser?
    suspend fun getUserByEmail(email: String): RegisteredUser?
    suspend fun emailExists(email: String): Boolean
}
