package com.mk.androidshowcase.domain.model.calendar

import kotlinx.datetime.LocalDate

data class DateRange(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
) {
    val isComplete: Boolean get() = startDate != null && endDate != null
    val isEmpty: Boolean get() = startDate == null && endDate == null
}

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val isDisabled: Boolean,
    val isWeekend: Boolean,
)

data class CalendarMonth(
    val year: Int,
    val month: Int,
    val days: List<CalendarDay>,
)

sealed interface SelectionState {
    data object Empty : SelectionState
    data class StartSelected(val startDate: LocalDate) : SelectionState
    data class RangeSelected(val range: DateRange) : SelectionState
}

enum class DaySelectionType {
    NONE,
    START,
    END,
    IN_RANGE,
    SINGLE,
}
