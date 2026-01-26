package com.mk.androidshowcase.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mk.androidshowcase.data.base.TransformToDomainModel
import com.mk.androidshowcase.domain.model.RegisteredUser

@Entity(tableName = "registered_users")
data class RegisteredUserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: Long,
) : TransformToDomainModel<RegisteredUser> {
    override fun transform(): RegisteredUser = RegisteredUser(
        id = id,
        name = name,
        email = email,
        password = password,
        createdAt = createdAt
    )

    constructor(user: RegisteredUser) : this(
        id = user.id,
        name = user.name,
        email = user.email,
        password = user.password,
        createdAt = user.createdAt
    )
}
