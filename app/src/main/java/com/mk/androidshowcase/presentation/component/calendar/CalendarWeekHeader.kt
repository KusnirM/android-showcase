package com.mk.androidshowcase.presentation.component.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mk.androidshowcase.presentation.component.text.labelSmall.TextLabelSmall

@Composable
fun CalendarWeekHeader(
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarColors.default(),
    strings: CalendarStrings = CalendarStrings.default(),
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        strings.weekDayLabels.forEach { dayLabel ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                TextLabelSmall(
                    text = dayLabel,
                    color = colors.weekHeaderTextColor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
