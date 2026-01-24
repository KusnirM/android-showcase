package mk.digital.androidshowcase.domain.useCase.settings

import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.domain.useCase.base.None
import mk.digital.androidshowcase.domain.useCase.base.UseCase
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<None, ThemeMode>() {
    override suspend fun run(params: None): ThemeMode = settingsRepository.getThemeMode()
}
