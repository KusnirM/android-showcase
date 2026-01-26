package com.mk.androidshowcase.data.repository

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import com.mk.androidshowcase.domain.repository.DateRepository
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor() : DateRepository {
    override fun today(): LocalDate {
        val now = Clock.System.now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}
