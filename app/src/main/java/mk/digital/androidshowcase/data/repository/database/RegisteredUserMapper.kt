package mk.digital.androidshowcase.data.repository.database

import mk.digital.androidshowcase.domain.model.RegisteredUser
import mk.digital.androidshowcase.data.database.RegisteredUser as RegisteredUserEntity

fun RegisteredUserEntity.transform() = RegisteredUser(
    id = id,
    name = name,
    email = email,
    password = password,
    createdAt = createdAt
)
