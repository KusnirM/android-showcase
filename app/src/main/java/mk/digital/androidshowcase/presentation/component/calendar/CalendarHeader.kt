package mk.digital.androidshowcase.presentation.component.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.calendar_header_format
import mk.digital.androidshowcase.shared.generated.resources.calendar_next_month
import mk.digital.androidshowcase.shared.generated.resources.calendar_previous_month
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarHeader(
    year: Int,
    month: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarColors.default(),
    strings: CalendarStrings = CalendarStrings.default(),
) {
    val monthName = strings.getMonthName(month)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(Res.string.calendar_previous_month),
                tint = colors.navigationIconColor,
            )
        }

        Text(
            text = stringResource(Res.string.calendar_header_format, monthName, year),
            color = colors.headerTextColor,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        )

        IconButton(onClick = onNextMonth) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.calendar_next_month),
                tint = colors.navigationIconColor,
            )
        }
    }
}
