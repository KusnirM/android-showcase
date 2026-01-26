package com.mk.androidshowcase.domain.repository

fun interface ClearableCache {
    suspend fun clear()
}
