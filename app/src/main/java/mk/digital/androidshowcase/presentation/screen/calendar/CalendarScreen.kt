package mk.digital.androidshowcase.presentation.screen.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.domain.model.calendar.DateRange
import mk.digital.androidshowcase.presentation.component.buttons.OutlinedButton
import mk.digital.androidshowcase.presentation.component.calendar.CalendarView
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space16
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace + space16,
        ),
        verticalArrangement = Arrangement.spacedBy(space4),
    ) {
        item {
            Column {
                TextHeadlineMediumPrimary(stringResource(R.string.calendar_title))
                TextBodyMediumNeutral80(stringResource(R.string.calendar_subtitle))
            }
        }

        state.today?.let { today ->
            item {
                AppElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(space4)
                ) {
                    CalendarView(
                        selectedRange = state.selectedRange,
                        onDateClick = viewModel::onDateClick,
                        today = today,
                        disabledDates = state.disabledDates,
                        minDate = state.minDate,
                        maxDate = state.maxDate,
                    )
                }
            }
        }

        item {
            AppElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(space4)
            ) {
                TextTitleLargeNeutral80(stringResource(R.string.calendar_selected_range))
                Spacer2()
                val rangeText = buildRangeText(state.selectedRange)
                TextBodyMediumNeutral80(rangeText)
            }
        }

        item {
            OutlinedButton(
                text = stringResource(R.string.calendar_clear_selection),
                onClick = viewModel::clearSelection,
            )
        }
    }
}

@Composable
private fun buildRangeText(range: DateRange): String {
    val start = range.startDate
    val end = range.endDate

    return when {
        start == null -> stringResource(R.string.calendar_no_dates_selected)
        end == null -> stringResource(R.string.calendar_start_date, start.toString())
        start == end -> stringResource(R.string.calendar_single_date, start.toString())
        else -> stringResource(R.string.calendar_range_format, start.toString(), end.toString())
    }
}
