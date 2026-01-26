package com.mk.androidshowcase.domain.repository

import kotlinx.datetime.LocalDate

interface DateRepository {
    fun today(): LocalDate
}
