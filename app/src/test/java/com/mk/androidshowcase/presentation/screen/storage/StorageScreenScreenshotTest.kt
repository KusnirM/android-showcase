package com.mk.androidshowcase.presentation.screen.storage

import com.android.resources.NightMode
import com.mk.androidshowcase.presentation.base.BaseScreenshotTest
import com.mk.androidshowcase.presentation.base.StateHolder
import com.mk.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class StorageScreenScreenshotTest(
    stateHolder: StateHolder<StorageUiState>,
    mode: NightMode,
) : BaseScreenshotTest<StorageUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> = generateParameterizedData(StorageScreenPreviewParams())
    }

    @Test
    fun storageScreen() {
        screenshot {
            StorageScreen(state = state)
        }
    }
}
