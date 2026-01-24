package mk.digital.androidshowcase.presentation.component.calendar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Immutable
data class CalendarColors(
    val dayTextColor: Color,
    val dayTextDisabledColor: Color,
    val otherMonthTextColor: Color,
    val weekendTextColor: Color,
    val todayBorderColor: Color,
    val selectedDayTextColor: Color,
    val selectedDayBackgroundColor: Color,
    val rangeBackgroundColor: Color,
    val headerTextColor: Color,
    val weekHeaderTextColor: Color,
    val navigationIconColor: Color,
) {
    companion object {
        @Composable
        @ReadOnlyComposable
        fun default(): CalendarColors {
            val appColors = MaterialTheme.appColorScheme
            return CalendarColors(
                dayTextColor = appColors.neutral80,
                dayTextDisabledColor = appColors.neutral40,
                otherMonthTextColor = appColors.neutral20,
                weekendTextColor = appColors.primary,
                todayBorderColor = appColors.neutral80,
                selectedDayTextColor = appColors.neutral0,
                selectedDayBackgroundColor = appColors.primary,
                rangeBackgroundColor = appColors.primary.copy(alpha = 0.15f),
                headerTextColor = appColors.neutral80,
                weekHeaderTextColor = appColors.neutral60,
                navigationIconColor = appColors.neutral80,
            )
        }
    }
}
