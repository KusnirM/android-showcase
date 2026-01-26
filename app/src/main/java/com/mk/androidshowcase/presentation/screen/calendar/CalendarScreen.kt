package com.mk.androidshowcase.presentation.screen.calendar

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDate
import com.mk.androidshowcase.R
import com.mk.androidshowcase.domain.model.calendar.DateRange
import com.mk.androidshowcase.presentation.component.buttons.OutlinedButton
import com.mk.androidshowcase.presentation.component.calendar.CalendarView
import com.mk.androidshowcase.presentation.component.cards.AppElevatedCard
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import com.mk.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import com.mk.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import com.mk.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import com.mk.androidshowcase.presentation.foundation.AppTheme
import com.mk.androidshowcase.presentation.foundation.floatingNavBarSpace
import com.mk.androidshowcase.presentation.foundation.space16
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CalendarScreen(
        state = state,
        onDateClick = viewModel::onDateClick,
        onClearSelection = viewModel::clearSelection
    )
}

@Composable
internal fun CalendarScreen(
    state: CalendarUiState,
    onDateClick: (LocalDate) -> Unit = {},
    onClearSelection: () -> Unit = {}
) {
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
                        onDateClick = onDateClick,
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
                onClick = onClearSelection,
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalendarScreenPreview(
    @PreviewParameter(CalendarScreenPreviewParams::class) state: CalendarUiState
) {
    AppTheme {
        CalendarScreen(state = state)
    }
}

internal class CalendarScreenPreviewParams : PreviewParameterProvider<CalendarUiState> {
    private val year = 2025
    private val month = 1
    private val today = LocalDate(year, month, 15)

    override val values = sequenceOf(
        CalendarUiState(today = today),
        CalendarUiState(
            today = today,
            selectedRange = DateRange(
                startDate = LocalDate(year, month, 10),
                endDate = LocalDate(year, month, 20)
            ),
            disabledDates = setOf(
                LocalDate(year, month, 7),
                LocalDate(year, month, 21)
            )
        ),
    )
}
