package com.mk.androidshowcase.domain.repository

import com.mk.androidshowcase.domain.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>

}
