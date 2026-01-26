package com.mk.androidshowcase.presentation.screen.register

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class RegisterScreenScreenshotTest(
    stateHolder: StateHolder<RegisterUiState>,
    mode: NightMode,
) : BaseScreenshotTest<RegisterUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(RegisterScreenPreviewParams())
    }

    @Test
    fun registerScreen() {
        screenshot {
            RegisterScreen(state = state)
        }
    }
}
