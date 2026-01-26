package com.mk.androidshowcase.domain.repository

import com.mk.androidshowcase.presentation.foundation.ThemeMode

interface SettingsRepository {
    suspend fun getThemeMode(): ThemeMode
    suspend fun setThemeMode(mode: ThemeMode)
}
