package mk.digital.androidshowcase.domain.useCase.settings

import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import mk.digital.androidshowcase.presentation.foundation.ThemeMode

class SetThemeModeUseCase(
    private val settingsRepository: SettingsRepository
) : UseCase<ThemeMode, Unit>() {
    override suspend fun run(params: ThemeMode) = settingsRepository.setThemeMode(params)
}
