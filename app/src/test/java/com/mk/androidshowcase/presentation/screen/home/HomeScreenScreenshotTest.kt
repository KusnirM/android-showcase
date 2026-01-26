package com.mk.androidshowcase.presentation.screen.home

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class HomeScreenScreenshotTest(
    stateHolder: StateHolder<HomeUiState>,
    mode: NightMode,
) : BaseScreenshotTest<HomeUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(HomeScreenPreviewParams())
    }

    @Test
    fun homeScreen() {
        screenshot {
                HomeScreen(state = state)
        }
    }
}
