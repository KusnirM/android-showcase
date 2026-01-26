package com.mk.androidshowcase.data.base

interface TransformToDomainModel<out DomainModel> {
    fun transform(): DomainModel
}
