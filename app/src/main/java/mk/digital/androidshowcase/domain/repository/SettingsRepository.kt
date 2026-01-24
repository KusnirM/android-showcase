package mk.digital.androidshowcase.domain.repository

import mk.digital.androidshowcase.presentation.foundation.ThemeMode

interface SettingsRepository {
    suspend fun getThemeMode(): ThemeMode
    suspend fun setThemeMode(mode: ThemeMode)
}
