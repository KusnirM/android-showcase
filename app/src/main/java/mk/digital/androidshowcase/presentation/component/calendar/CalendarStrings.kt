package mk.digital.androidshowcase.presentation.component.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_april
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_august
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_december
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_february
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_january
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_july
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_june
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_march
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_may
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_november
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_october
import mk.digital.androidshowcase.shared.generated.resources.calendar_month_september
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_fri
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_mon
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_sat
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_sun
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_thu
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_tue
import mk.digital.androidshowcase.shared.generated.resources.calendar_weekday_wed
import org.jetbrains.compose.resources.stringResource

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
                stringResource(Res.string.calendar_weekday_mon),
                stringResource(Res.string.calendar_weekday_tue),
                stringResource(Res.string.calendar_weekday_wed),
                stringResource(Res.string.calendar_weekday_thu),
                stringResource(Res.string.calendar_weekday_fri),
                stringResource(Res.string.calendar_weekday_sat),
                stringResource(Res.string.calendar_weekday_sun),
            ),
            monthNames = listOf(
                stringResource(Res.string.calendar_month_january),
                stringResource(Res.string.calendar_month_february),
                stringResource(Res.string.calendar_month_march),
                stringResource(Res.string.calendar_month_april),
                stringResource(Res.string.calendar_month_may),
                stringResource(Res.string.calendar_month_june),
                stringResource(Res.string.calendar_month_july),
                stringResource(Res.string.calendar_month_august),
                stringResource(Res.string.calendar_month_september),
                stringResource(Res.string.calendar_month_october),
                stringResource(Res.string.calendar_month_november),
                stringResource(Res.string.calendar_month_december),
            ),
        )
    }
}
