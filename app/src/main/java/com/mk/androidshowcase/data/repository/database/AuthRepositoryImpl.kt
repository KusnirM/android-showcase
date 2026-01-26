package com.mk.androidshowcase.data.repository.database

import com.mk.androidshowcase.data.database.dao.RegisteredUserDao
import com.mk.androidshowcase.data.database.entity.RegisteredUserEntity
import com.mk.androidshowcase.domain.model.RegisteredUser
import com.mk.androidshowcase.domain.repository.AuthRepository
import javax.inject.Inject
import kotlin.time.Clock

class AuthRepositoryImpl @Inject constructor(
    private val registeredUserDao: RegisteredUserDao
) : AuthRepository {

    override suspend fun register(name: String, email: String, password: String): RegisteredUser {
        val createdAt = Clock.System.now().toEpochMilliseconds()
        val entity = RegisteredUserEntity(
            name = name,
            email = email,
            password = password,
            createdAt = createdAt
        )
        registeredUserDao.insert(entity)
        return registeredUserDao.getByEmail(email)!!.transform()
    }

    override suspend fun login(email: String, password: String): RegisteredUser? {
        val user = registeredUserDao.getByEmail(email)?.transform()
        return if (user?.password == password) user else null
    }

    override suspend fun getUserByEmail(email: String): RegisteredUser? {
        return registeredUserDao.getByEmail(email)?.transform()
    }

    override suspend fun emailExists(email: String): Boolean {
        return registeredUserDao.emailExists(email)
    }
}
