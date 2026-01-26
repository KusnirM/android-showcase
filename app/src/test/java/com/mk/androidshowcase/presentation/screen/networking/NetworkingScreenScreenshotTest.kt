package com.mk.androidshowcase.presentation.screen.networking

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class NetworkingScreenScreenshotTest(
    stateHolder: StateHolder<NetworkingUiState>,
    mode: NightMode,
) : BaseScreenshotTest<NetworkingUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(NetworkingScreenPreviewParams())
    }

    @Test
    fun networkingScreen() {
        screenshot {
            NetworkingScreen(state = state)
        }
    }
}
