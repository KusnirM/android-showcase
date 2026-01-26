package com.mk.androidshowcase.presentation.screen.main

import dagger.hilt.android.lifecycle.HiltViewModel
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import com.mk.androidshowcase.presentation.base.BaseViewModel
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

data class MainState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase
) : BaseViewModel<MainState>(MainState()) {

    override fun loadInitialData() {
        loadThemeMode()
    }

    private fun loadThemeMode() {
        execute(
            action = { getThemeModeUseCase() },
            onSuccess = { mode -> newState { it.copy(themeMode = mode) } }
        )
    }

    fun setThemeMode(mode: ThemeMode) {
        newState { it.copy(themeMode = mode) }
    }
}
