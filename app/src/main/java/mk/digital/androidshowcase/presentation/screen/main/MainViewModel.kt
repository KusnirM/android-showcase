package mk.digital.androidshowcase.presentation.screen.main

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

data class MainState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val getThemeModeUseCase: GetThemeModeUseCase
) : BaseViewModel<MainState>(application, MainState()) {

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
