package com.mk.androidshowcase.presentation.screen.settings

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class SettingsScreenScreenshotTest(
    stateHolder: StateHolder<SettingsState>,
    mode: NightMode,
) : BaseScreenshotTest<SettingsState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(SettingsScreenPreviewParams())
    }

    @Test
    fun settingsScreen() {
        screenshot {
            SettingsScreen(state = state)
        }
    }
}
