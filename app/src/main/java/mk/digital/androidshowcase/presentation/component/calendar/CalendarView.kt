package mk.digital.androidshowcase.presentation.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import mk.digital.androidshowcase.domain.model.calendar.DateRange
import mk.digital.androidshowcase.presentation.foundation.space2

@Composable
fun CalendarView(
    selectedRange: DateRange,
    onDateClick: (LocalDate) -> Unit,
    today: LocalDate,
    modifier: Modifier = Modifier,
    disabledDates: Set<LocalDate> = emptySet(),
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    initialMonth: LocalDate = today,
    colors: CalendarColors = CalendarColors.default(),
    strings: CalendarStrings = CalendarStrings.default(),
) {
    var currentYear by remember { mutableStateOf(initialMonth.year) }
    var currentMonth by remember { mutableStateOf(initialMonth.month.number) }

    val calendarMonth = remember(currentYear, currentMonth, today, disabledDates, minDate, maxDate) {
        CalendarUtils.generateMonth(
            year = currentYear,
            month = currentMonth,
            today = today,
            disabledDates = disabledDates,
            minDate = minDate,
            maxDate = maxDate,
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        CalendarHeader(
            year = currentYear,
            month = currentMonth,
            onPreviousMonth = {
                val (newYear, newMonth) = CalendarUtils.getPreviousMonth(currentYear, currentMonth)
                currentYear = newYear
                currentMonth = newMonth
            },
            onNextMonth = {
                val (newYear, newMonth) = CalendarUtils.getNextMonth(currentYear, currentMonth)
                currentYear = newYear
                currentMonth = newMonth
            },
            colors = colors,
            strings = strings,
        )

        Spacer(modifier = Modifier.height(space2))

        CalendarWeekHeader(colors = colors, strings = strings)

        Spacer(modifier = Modifier.height(space2))

        CalendarGrid(
            days = calendarMonth.days,
            selectedRange = selectedRange,
            onDayClick = onDateClick,
            colors = colors,
        )
    }
}
