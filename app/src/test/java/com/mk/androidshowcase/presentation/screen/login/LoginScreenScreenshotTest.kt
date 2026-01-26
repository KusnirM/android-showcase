package com.mk.androidshowcase.presentation.screen.login

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class LoginScreenScreenshotTest(
    stateHolder: StateHolder<LoginUiState>,
    mode: NightMode,
) : BaseScreenshotTest<LoginUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(LoginScreenPreviewParams())
    }

    @Test
    fun loginScreen() {
        screenshot {
            LoginScreen(state = state)
        }
    }
}
