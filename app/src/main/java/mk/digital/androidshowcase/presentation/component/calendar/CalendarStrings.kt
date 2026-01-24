package mk.digital.androidshowcase.presentation.component.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import mk.digital.androidshowcase.R

private const val MONTH_TO_INDEX_OFFSET = 1

@Immutable
data class CalendarStrings(
    val weekDayLabels: List<String>,
    val monthNames: List<String>,
) {
    fun getMonthName(month: Int): String = monthNames.getOrElse(month - MONTH_TO_INDEX_OFFSET) { "" }

    companion object {
        @Composable
        fun default(): CalendarStrings = CalendarStrings(
            weekDayLabels = listOf(
                stringResource(R.string.calendar_weekday_mon),
                stringResource(R.string.calendar_weekday_tue),
                stringResource(R.string.calendar_weekday_wed),
                stringResource(R.string.calendar_weekday_thu),
                stringResource(R.string.calendar_weekday_fri),
                stringResource(R.string.calendar_weekday_sat),
                stringResource(R.string.calendar_weekday_sun),
            ),
            monthNames = listOf(
                stringResource(R.string.calendar_month_january),
                stringResource(R.string.calendar_month_february),
                stringResource(R.string.calendar_month_march),
                stringResource(R.string.calendar_month_april),
                stringResource(R.string.calendar_month_may),
                stringResource(R.string.calendar_month_june),
                stringResource(R.string.calendar_month_july),
                stringResource(R.string.calendar_month_august),
                stringResource(R.string.calendar_month_september),
                stringResource(R.string.calendar_month_october),
                stringResource(R.string.calendar_month_november),
                stringResource(R.string.calendar_month_december),
            ),
        )
    }
}
