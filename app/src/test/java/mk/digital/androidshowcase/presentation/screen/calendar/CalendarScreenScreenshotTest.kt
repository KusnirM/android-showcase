package mk.digital.androidshowcase.presentation.screen.calendar

import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.BaseScreenshotTest
import mk.digital.androidshowcase.presentation.base.StateHolder
import mk.digital.androidshowcase.presentation.base.generateParameterizedData
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
