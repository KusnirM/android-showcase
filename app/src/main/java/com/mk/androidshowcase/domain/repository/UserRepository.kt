package com.mk.androidshowcase.domain.repository

import com.mk.androidshowcase.domain.model.User

interface UserRepository {

    suspend fun getUser(id: Int): User

    suspend fun getUsers(): List<User>

}
