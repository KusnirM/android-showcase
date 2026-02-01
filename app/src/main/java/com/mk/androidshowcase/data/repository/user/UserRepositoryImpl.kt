package com.mk.androidshowcase.data.repository.user

import com.mk.androidshowcase.data.base.transformAll
import com.mk.androidshowcase.domain.model.User
import com.mk.androidshowcase.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val client: UserClient
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return client.fetchUsers().transformAll()
    }
}
