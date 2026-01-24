package mk.digital.androidshowcase.data.base

interface TransformToDomainModel<out DomainModel> {
    fun transform(): DomainModel
}
