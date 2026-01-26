package com.mk.androidshowcase.presentation.screen.database

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class DatabaseScreenScreenshotTest(
    stateHolder: StateHolder<DatabaseUiState>,
    mode: NightMode,
) : BaseScreenshotTest<DatabaseUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(DatabaseScreenPreviewParams())
    }

    @Test
    fun databaseScreen() {
        screenshot {
            DatabaseScreen(state = state)
        }
    }
}
