package com.mk.androidshowcase.presentation.screen.calendar

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class CalendarScreenScreenshotTest(
    stateHolder: StateHolder<CalendarUiState>,
    mode: NightMode,
) : BaseScreenshotTest<CalendarUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(CalendarScreenPreviewParams())
    }

    @Test
    fun calendarScreen() {
        screenshot {
            CalendarScreen(state = state)
        }
    }
}
