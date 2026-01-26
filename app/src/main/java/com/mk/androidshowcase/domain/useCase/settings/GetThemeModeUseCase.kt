package com.mk.androidshowcase.domain.useCase.settings

import com.mk.androidshowcase.domain.repository.SettingsRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<None, ThemeMode>() {
    override suspend fun run(params: None): ThemeMode = settingsRepository.getThemeMode()
}
