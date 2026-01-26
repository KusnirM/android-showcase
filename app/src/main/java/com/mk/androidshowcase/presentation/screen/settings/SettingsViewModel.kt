package com.mk.androidshowcase.presentation.screen.settings

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.mk.androidshowcase.BuildConfig
import com.mk.androidshowcase.R
import com.mk.androidshowcase.domain.useCase.analytics.RecordExceptionUseCase
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import com.mk.androidshowcase.domain.useCase.settings.SetThemeModeUseCase
import com.mk.androidshowcase.presentation.base.BaseViewModel
import com.mk.androidshowcase.presentation.base.NavEvent
import com.mk.androidshowcase.presentation.foundation.AppIcons
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SettingsState(
    val themeModeState: ThemeModeState = ThemeModeState.SYSTEM,
    val currentLanguage: LanguageState = LanguageState.EN,
    val showThemeDialog: Boolean = false,
) {
    val showCrashButton: Boolean
        get() = BuildConfig.DEBUG
    val versionName: String = BuildConfig.VERSION_NAME
    val versionCode: String = BuildConfig.VERSION_CODE.toString()
}

enum class ThemeModeState(@get:StringRes val textId: Int, val mode: ThemeMode) {
    LIGHT(R.string.settings_theme_light, ThemeMode.LIGHT),
    DARK(R.string.settings_theme_dark, ThemeMode.DARK),
    SYSTEM(R.string.settings_theme_system, ThemeMode.SYSTEM);

    companion object {
        fun fromMode(mode: ThemeMode): ThemeModeState =
            entries.find { it.mode == mode } ?: SYSTEM
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
    private val recordExceptionUseCase: RecordExceptionUseCase,
) : BaseViewModel<SettingsState>(SettingsState()) {

    override fun loadInitialData() {
        loadThemeMode()
        loadCurrentLanguage()
    }

    override fun onResume() {
        loadCurrentLanguage()
    }

    private fun loadCurrentLanguage() {
        val appLocales = AppCompatDelegate.getApplicationLocales()
        val locale = appLocales[0] ?: LocaleListCompat.getAdjustedDefault()[0]
        val language = LanguageState.fromCode(locale?.language)
        newState { it.copy(currentLanguage = language) }
    }

    private fun loadThemeMode() {
        execute(
            action = { getThemeModeUseCase() },
            onSuccess = { themeMode -> newState { it.copy(themeModeState = ThemeModeState.fromMode(themeMode)) } }
        )
    }

    fun setThemeMode(themeModeState: ThemeModeState) {
        execute(
            action = { setThemeModeUseCase(themeModeState.mode) },
            onSuccess = {
                newState { it.copy(themeModeState = themeModeState) }
                navigate(SettingNavEvents.ThemeChanged(themeModeState.mode))
            }
        )
    }

    fun showThemeDialog() {
        newState { it.copy(showThemeDialog = true) }
    }

    fun hideThemeDialog() {
        newState { it.copy(showThemeDialog = false) }
    }

    fun onLanguageSelected(language: LanguageState) {
        newState { it.copy(currentLanguage = language) }
        navigate(SettingNavEvents.SetLocaleTag(language.code))
    }

    fun logout() {
        navigate(SettingNavEvents.Logout)
    }

    fun triggerTestCrash() {
        val exception = RuntimeException("Test Crash for Firebase Crashlytics")
        execute(
            action = { recordExceptionUseCase(exception) },
            onSuccess = { throw exception }
        )
    }
}

enum class LanguageState(
    @get:StringRes val stringRes: Int,
    val icon: ImageVector,
    val code: String,
) {
    SK(R.string.language_sk, AppIcons.FlagSK, "sk"),
    EN(R.string.language_en, AppIcons.FlagEN, "en");

    companion object {
        fun fromCode(code: String?): LanguageState =
            entries.find { it.code == code } ?: EN
    }
}

sealed interface SettingNavEvents : NavEvent {

    // Android
    data class SetLocaleTag(val tag: String) : SettingNavEvents

    data object Logout : SettingNavEvents

    data class ThemeChanged(val mode: ThemeMode) : SettingNavEvents
}
