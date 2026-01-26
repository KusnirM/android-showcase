package com.mk.androidshowcase.data.dto

import kotlinx.serialization.Serializable
import com.mk.androidshowcase.data.base.TransformToDomainModel
import com.mk.androidshowcase.domain.model.User

@Serializable
data class UserDTO(
    val address: AddressDTO,
    val email: String,
    val id: Int,
    val name: String,
) : TransformToDomainModel<User> {
    override fun transform(): User = User(address = address.transform(), email = email, id = id, name = name)
}
