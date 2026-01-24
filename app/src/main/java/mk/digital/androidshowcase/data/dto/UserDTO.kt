package mk.digital.androidshowcase.data.dto

import kotlinx.serialization.Serializable
import mk.digital.androidshowcase.data.base.TransformToDomainModel
import mk.digital.androidshowcase.domain.model.User

@Serializable
data class UserDTO(
    val address: AddressDTO,
    val email: String,
    val id: Int,
    val name: String,
) : TransformToDomainModel<User> {
    override fun transform(): User = User(address = address.transform(), email = email, id = id, name = name)
}
