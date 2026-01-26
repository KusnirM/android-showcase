package com.mk.androidshowcase.data.repository

import com.mk.androidshowcase.data.local.preferences.PersistentPreferences
import com.mk.androidshowcase.domain.repository.SettingsRepository
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
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
