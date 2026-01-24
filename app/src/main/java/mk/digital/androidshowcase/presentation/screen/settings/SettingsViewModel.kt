package mk.digital.androidshowcase.presentation.screen.settings

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import mk.digital.androidshowcase.BuildConfig
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.data.analytics.AnalyticsClient
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import mk.digital.androidshowcase.domain.useCase.settings.SetThemeModeUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.foundation.AppIcons
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import mk.digital.androidshowcase.util.getCurrentLanguageTag

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

class SettingsViewModel(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
    private val analyticsClient: AnalyticsClient,
) : BaseViewModel<SettingsState>(
    SettingsState()
) {

    override fun loadInitialData() {
        loadThemeMode()
        loadCurrentLanguage()
    }

    override fun onResumed() {
        loadCurrentLanguage()
    }

    private fun loadCurrentLanguage() {
        val currentTag = getCurrentLanguageTag()
        val language = LanguageState.fromTag(currentTag)
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

    fun onLanguageNavEvent(event: SettingNavEvents) {
        navigate(event)
    }

    fun logout() {
        navigate(SettingNavEvents.Logout)
    }

    fun triggerTestCrash() {
        val exception = RuntimeException("Test Crash for Firebase Crashlytics")
        analyticsClient.recordException(exception)
        throw exception
    }
}

enum class LanguageState(
    @get:StringRes val stringRes: Int,
    val icon: ImageVector,
    val tag: String,
) {
    SK(R.string.language_sk, AppIcons.FlagSK, "sk-SK"),
    EN(R.string.language_en, AppIcons.FlagEN, "en-US");

    companion object {
        fun fromTag(tag: String?): LanguageState =
            entries.find {
                it.tag.substringBefore('-') == tag
                    ?.lowercase()
                    ?.replace('_', '-')
                    ?.substringBefore('-')
            }
                ?: EN
    }
}

sealed interface SettingNavEvents : NavEvent {

    // Android
    data class SetLocaleTag(val tag: String) : SettingNavEvents

    // iOS
    data object ToSettings : SettingNavEvents

    data object Logout : SettingNavEvents

    data class ThemeChanged(val mode: ThemeMode) : SettingNavEvents
}
