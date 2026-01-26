package com.mk.androidshowcase.presentation.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mk.androidshowcase.domain.model.calendar.CalendarDay
import com.mk.androidshowcase.presentation.foundation.halfSpace
import com.mk.androidshowcase.presentation.foundation.quarterSpace
import com.mk.androidshowcase.domain.model.calendar.DaySelectionType

@Composable
fun CalendarDayCell(
    day: CalendarDay,
    selectionType: DaySelectionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarColors.default(),
) {
    val labelLarge = MaterialTheme.typography.labelLarge

    val textColor = remember(day.isCurrentMonth, day.isDisabled, day.isWeekend, selectionType, colors) {
        when {
            !day.isCurrentMonth -> colors.dayTextDisabledColor
            day.isDisabled -> colors.dayTextDisabledColor
            selectionType == DaySelectionType.START ||
                    selectionType == DaySelectionType.END ||
                    selectionType == DaySelectionType.SINGLE -> colors.selectedDayTextColor

            day.isWeekend -> colors.weekendTextColor
            else -> colors.dayTextColor
        }
    }

    val fontWeight = remember(day.isWeekend, day.isCurrentMonth, day.isDisabled) {
        when {
            day.isWeekend && day.isCurrentMonth && !day.isDisabled -> FontWeight.Bold
            else -> FontWeight.Normal
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .rangeBackground(
                selectionType = selectionType,
                rangeColor = colors.rangeBackgroundColor,
            )
            .then(
                if (day.isToday && day.isCurrentMonth && selectionType == DaySelectionType.NONE) {
                    Modifier
                        .padding(halfSpace)
                        .border(quarterSpace, colors.todayBorderColor, CircleShape)
                } else if (selectionType == DaySelectionType.START ||
                    selectionType == DaySelectionType.END ||
                    selectionType == DaySelectionType.SINGLE
                ) {
                    Modifier
                        .padding(halfSpace)
                        .clip(CircleShape)
                        .background(colors.selectedDayBackgroundColor)
                } else {
                    Modifier.padding(halfSpace)
                },
            )
            .then(
                if (day.isCurrentMonth && !day.isDisabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.day.toString(),
            color = textColor,
            style = labelLarge.copy(fontWeight = fontWeight),
            textAlign = TextAlign.Center,
        )
    }
}

private fun Modifier.rangeBackground(
    selectionType: DaySelectionType,
    rangeColor: Color,
): Modifier = this.drawBehind {
    when (selectionType) {
        DaySelectionType.START -> {
            // Draw right half rectangle for range continuation
            drawRect(
                color = rangeColor,
                topLeft = Offset(size.width / 2, 0f),
                size = Size(size.width / 2, size.height),
            )
        }

        DaySelectionType.END -> {
            // Draw left half rectangle for range continuation
            drawRect(
                color = rangeColor,
                topLeft = Offset(0f, 0f),
                size = Size(size.width / 2, size.height),
            )
        }

        DaySelectionType.IN_RANGE -> {
            // Draw full width background
            drawRect(
                color = rangeColor,
                topLeft = Offset(0f, 0f),
                size = size,
            )
        }

        DaySelectionType.SINGLE, DaySelectionType.NONE -> {
            // No range background
        }
    }
}
