package com.mk.androidshowcase.presentation.component.calendar

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import com.mk.androidshowcase.domain.model.calendar.CalendarDay
import com.mk.androidshowcase.domain.model.calendar.CalendarMonth
import com.mk.androidshowcase.domain.model.calendar.DateRange
import com.mk.androidshowcase.domain.model.calendar.DaySelectionType

object CalendarUtils {

    const val DAYS_PER_WEEK = 7
    const val WEEKS_TO_DISPLAY = 6
    const val TOTAL_CELLS = DAYS_PER_WEEK * WEEKS_TO_DISPLAY
    private const val FIRST_CELL_INDEX = 0
    private const val FIRST_DAY_OF_MONTH = 1
    private const val ISO_TO_ZERO_BASED_OFFSET = 1
    private const val INCREMENT = 1

    fun generateMonth(
        year: Int,
        month: Int,
        today: LocalDate,
        disabledDates: Set<LocalDate> = emptySet(),
        minDate: LocalDate? = null,
        maxDate: LocalDate? = null,
    ): CalendarMonth {
        val firstDayOfMonth = LocalDate(year, month, FIRST_DAY_OF_MONTH)

        // Calculate how many days from previous month to show (Mon=0, Tue=1, ..., Sun=6)
        val daysFromPrevMonth = firstDayOfMonth.dayOfWeek.isoDayNumber - ISO_TO_ZERO_BASED_OFFSET
        val gridStartDate = firstDayOfMonth.minus(daysFromPrevMonth, DateTimeUnit.DAY)

        val days = mutableListOf<CalendarDay>()

        for (i in FIRST_CELL_INDEX until TOTAL_CELLS) {
            val currentDate = gridStartDate.plus(i, DateTimeUnit.DAY)
            val isCurrentMonth = currentDate.month.number == month && currentDate.year == year
            val isWeekend = currentDate.dayOfWeek == DayOfWeek.SATURDAY ||
                    currentDate.dayOfWeek == DayOfWeek.SUNDAY
            val isDisabled = isDateDisabled(currentDate, disabledDates, minDate, maxDate)

            days.add(
                CalendarDay(
                    date = currentDate,
                    isCurrentMonth = isCurrentMonth,
                    isToday = currentDate == today,
                    isDisabled = isDisabled,
                    isWeekend = isWeekend,
                ),
            )
        }

        return CalendarMonth(year = year, month = month, days = days)
    }

    fun getDaySelectionType(
        date: LocalDate,
        selectedRange: DateRange,
    ): DaySelectionType {
        val start = selectedRange.startDate ?: return DaySelectionType.NONE
        val end = selectedRange.endDate

        return when {
            end == null && date == start -> DaySelectionType.SINGLE
            end != null && start == end && date == start -> DaySelectionType.SINGLE
            date == start -> DaySelectionType.START
            date == end -> DaySelectionType.END
            end != null && date > start && date < end -> DaySelectionType.IN_RANGE
            else -> DaySelectionType.NONE
        }
    }

    fun getPreviousMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == Month.JANUARY.number) {
            Pair(year - INCREMENT, Month.DECEMBER.number)
        } else {
            Pair(year, month - INCREMENT)
        }
    }

    fun getNextMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == Month.DECEMBER.number) {
            Pair(year + INCREMENT, Month.JANUARY.number)
        } else {
            Pair(year, month + INCREMENT)
        }
    }

    private fun isDateDisabled(
        date: LocalDate,
        disabledDates: Set<LocalDate>,
        minDate: LocalDate?,
        maxDate: LocalDate?,
    ): Boolean {
        if (date in disabledDates) return true
        if (minDate != null && date < minDate) return true
        if (maxDate != null && date > maxDate) return true
        return false
    }
}
