package com.mk.androidshowcase.presentation.screen.notifications

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class NotificationsScreenScreenshotTest(
    stateHolder: StateHolder<NotificationsUiState>,
    mode: NightMode,
) : BaseScreenshotTest<NotificationsUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(NotificationsScreenPreviewParams())
    }

    @Test
    fun notificationsScreen() {
        screenshot {
            NotificationsScreen(state = state)
        }
    }
}
