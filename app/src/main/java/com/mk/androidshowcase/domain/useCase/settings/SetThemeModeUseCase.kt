package com.mk.androidshowcase.domain.useCase.settings

import com.mk.androidshowcase.domain.repository.SettingsRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<ThemeMode, Unit>() {
    override suspend fun run(params: ThemeMode) = settingsRepository.setThemeMode(params)
}
