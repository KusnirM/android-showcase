package mk.digital.androidshowcase.data.repository.database

import mk.digital.androidshowcase.data.database.dao.RegisteredUserDao
import mk.digital.androidshowcase.data.database.entity.RegisteredUserEntity
import mk.digital.androidshowcase.domain.model.RegisteredUser
import mk.digital.androidshowcase.domain.repository.AuthRepository
import kotlin.time.Clock

class AuthRepositoryImpl(
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
        return registeredUserDao.getByEmail(email)!!.toDomain()
    }

    override suspend fun login(email: String, password: String): RegisteredUser? {
        val user = registeredUserDao.getByEmail(email)?.toDomain()
        return if (user?.password == password) user else null
    }

    override suspend fun getUserByEmail(email: String): RegisteredUser? {
        return registeredUserDao.getByEmail(email)?.toDomain()
    }

    override suspend fun emailExists(email: String): Boolean {
        return registeredUserDao.emailExists(email)
    }
}
