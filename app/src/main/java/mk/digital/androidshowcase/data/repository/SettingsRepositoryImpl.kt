package mk.digital.androidshowcase.data.repository

import mk.digital.androidshowcase.data.local.preferences.PersistentPreferences
import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.presentation.foundation.ThemeMode

class SettingsRepositoryImpl(
    private val persistentPreferences: PersistentPreferences
) : SettingsRepository {

    override suspend fun getThemeMode(): ThemeMode {
        val mode = persistentPreferences.getThemeMode()
        return ThemeMode.entries.find { it.name == mode } ?: ThemeMode.SYSTEM
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        persistentPreferences.setThemeMode(mode.name)
    }
}
