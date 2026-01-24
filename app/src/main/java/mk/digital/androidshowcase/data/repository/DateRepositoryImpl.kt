package mk.digital.androidshowcase.data.repository

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import mk.digital.androidshowcase.domain.repository.DateRepository

class DateRepositoryImpl : DateRepository {
    override fun today(): LocalDate {
        val now = Clock.System.now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}
