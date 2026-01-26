package com.mk.androidshowcase.presentation.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import com.mk.androidshowcase.domain.model.calendar.CalendarDay
import com.mk.androidshowcase.domain.model.calendar.DateRange

@Composable
fun CalendarGrid(
    days: List<CalendarDay>,
    selectedRange: DateRange,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarColors.default(),
) {
    Column(modifier = modifier.fillMaxWidth()) {
        for (weekIndex in 0 until CalendarUtils.WEEKS_TO_DISPLAY) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayIndex in 0 until CalendarUtils.DAYS_PER_WEEK) {
                    val index = weekIndex * CalendarUtils.DAYS_PER_WEEK + dayIndex
                    if (index < days.size) {
                        val day = days[index]
                        val selectionType = CalendarUtils.getDaySelectionType(
                            date = day.date,
                            selectedRange = selectedRange,
                        )

                        CalendarDayCell(
                            day = day,
                            selectionType = selectionType,
                            onClick = { onDayClick(day.date) },
                            modifier = Modifier.weight(1f),
                            colors = colors,
                        )
                    }
                }
            }
        }
    }
}
