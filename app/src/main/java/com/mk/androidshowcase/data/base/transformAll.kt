package com.mk.androidshowcase.data.base

fun <T, R> List<T>.transformAll(): List<R> where T : TransformToDomainModel<R> =
    this.map { it.transform() }
