package mk.digital.androidshowcase.presentation.screen.notifications

import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.BaseScreenshotTest
import mk.digital.androidshowcase.presentation.base.StateHolder
import mk.digital.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class NotificationsScreenScreenshotTest(
    stateHolder: StateHolder<NotificationsUiState>,
    mode: NightMode,
) : BaseScreenshotTest<NotificationsUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}_{1}")
        fun data(): Collection<*> = generateParameterizedData(NotificationsScreenPreviewParams())
    }

    @Test
    fun notificationsScreen() {
        screenshot {
            NotificationsScreen(state = state)
        }
    }
}
