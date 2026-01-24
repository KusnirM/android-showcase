package mk.digital.androidshowcase.data.dto

import kotlinx.serialization.Serializable
import mk.digital.androidshowcase.data.base.TransformToDomainModel
import mk.digital.androidshowcase.domain.model.Address

@Serializable
data class AddressDTO(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String
) : TransformToDomainModel<Address> {
    override fun transform(): Address = Address(city = city, street = street, suite = suite, zipcode = zipcode)
}
