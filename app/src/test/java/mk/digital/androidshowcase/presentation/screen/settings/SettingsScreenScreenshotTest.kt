package mk.digital.androidshowcase.presentation.screen.settings

import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.BaseScreenshotTest
import mk.digital.androidshowcase.presentation.base.StateHolder
import mk.digital.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class SettingsScreenScreenshotTest(
    stateHolder: StateHolder<SettingsState>,
    mode: NightMode,
) : BaseScreenshotTest<SettingsState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}_{1}")
        fun data(): Collection<*> = generateParameterizedData(SettingsScreenPreviewParams())
    }

    @Test
    fun settingsScreen() {
        screenshot {
            SettingsScreen(state = state)
        }
    }
}
