package mk.digital.androidshowcase.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.LifecycleEffect
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.component.AppAlertDialog
import mk.digital.androidshowcase.presentation.component.AppRadioButton
import mk.digital.androidshowcase.presentation.component.AvatarState
import mk.digital.androidshowcase.presentation.component.AvatarView
import mk.digital.androidshowcase.presentation.component.buttons.AppTextButtonError
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.image.AppIconPrimary
import mk.digital.androidshowcase.presentation.component.imagepicker.ImagePickerView
import mk.digital.androidshowcase.presentation.component.imagepicker.ImagePickerViewModel
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral100
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargePrimary
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargePrimary
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun SettingsScreen(
    router: NavRouter<Route>,
    viewModel: SettingsViewModel = hiltViewModel(),
    imagePickerViewModel: ImagePickerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val imagePickerState by imagePickerViewModel.state.collectAsStateWithLifecycle()

    LifecycleEffect(onResume = viewModel::onResumed)

    val avatarState = when {
        imagePickerState.isLoading -> AvatarState.Loading
        imagePickerState.imageBitmap != null -> AvatarState.Loaded(imagePickerState.imageBitmap!!)
        else -> AvatarState.Empty
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace
        ),
        verticalArrangement = Arrangement.spacedBy(space4)
    ) {
        item {
            TextTitleLargePrimary(stringResource(R.string.settings_profile))
        }

        item {
            AppElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { imagePickerViewModel.showDialog() }
            ) {
                ProfileItem(
                    avatarState = avatarState,
                    title = stringResource(R.string.settings_profile_photo),
                    hint = stringResource(R.string.settings_profile_photo_hint)
                )
            }
        }

        item {
            TextTitleLargePrimary(stringResource(R.string.settings_appearance))
        }

        item {
            AppElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.showThemeDialog() }
            ) {
                SettingsItem(
                    icon = {
                        AppIconPrimary(
                            Icons.Outlined.DarkMode,
                            contentDescription = stringResource(R.string.settings_theme)
                        )
                    },
                    title = stringResource(R.string.settings_theme),
                    value = stringResource(state.themeModeState.textId)
                )
            }
        }

        item {
            LanguageSelector(
                currentLanguage = state.currentLanguage,
                onNavigate = viewModel::onLanguageNavEvent
            )
        }

        if (state.showCrashButton) {
            item {
                AppElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = viewModel::triggerTestCrash
                ) {
                    SettingsItem(
                        icon = {
                            AppIconPrimary(
                                Icons.Outlined.BugReport,
                                contentDescription = "Test Crash"
                            )
                        },
                        title = "Test Crash",
                        value = "Trigger a crash to test Crashlytics"
                    )
                }
            }
        }

        item {
            VersionFooter(
                versionName = state.versionName,
                versionCode = state.versionCode
            )
        }

        item {
            AppTextButtonError(
                text = stringResource(R.string.settings_logout),
                onClick = viewModel::logout,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    ImagePickerView(viewModel = imagePickerViewModel)

    if (state.showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = state.themeModeState,
            onThemeSelected = { themeModeState ->
                viewModel.setThemeMode(themeModeState)
                viewModel.hideThemeDialog()
            },
            onDismiss = viewModel::hideThemeDialog
        )
    }

    SettingsNavEvents(router, viewModel.navEvent)
}

@Composable
private fun ProfileItem(
    avatarState: AvatarState,
    title: String,
    hint: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space4)
    ) {
        AvatarView(state = avatarState)
        Column(modifier = Modifier.weight(1f)) {
            TextBodyLargePrimary(title)
            Spacer2()
            TextBodyMediumNeutral80(hint)
        }
    }
}

@Composable
private fun SettingsItem(
    icon: @Composable () -> Unit,
    title: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space4)
    ) {
        icon()
        Column(modifier = Modifier.weight(1f)) {
            TextBodyLargePrimary(title)
            Spacer2()
            TextBodyMediumNeutral80(value)
        }
    }
}

@Composable
private fun ThemeSelectionDialog(
    currentTheme: ThemeModeState,
    onThemeSelected: (ThemeModeState) -> Unit,
    onDismiss: () -> Unit,
) {
    AppAlertDialog(
        title = stringResource(R.string.settings_theme),
        onDismissRequest = onDismiss,
    ) {
        Column {
            ThemeModeState.entries.forEach { themeModeState ->
                ThemeOption(
                    title = stringResource(themeModeState.textId),
                    selected = currentTheme == themeModeState,
                    onClick = { onThemeSelected(themeModeState) }
                )
            }
        }
    }
}

@Composable
private fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = space4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppRadioButton(selected = selected, onClick = onClick)
        TextBodyLargeNeutral100(title)
    }
}

@Composable
private fun VersionFooter(
    versionName: String,
    versionCode: String,
) {
    TextBodySmallNeutral80(
        text = stringResource(R.string.settings_version, versionName, versionCode),
        modifier = Modifier.fillMaxWidth(),
        textAlign = androidx.compose.ui.text.style.TextAlign.End,
    )
}

@Composable
private fun SettingsNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>,
) {
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is SettingNavEvents.SetLocaleTag -> router.setLocale(event.tag)
            is SettingNavEvents.ToSettings -> router.openSettings()
            is SettingNavEvents.Logout -> router.replaceAll(Route.Login)
            is SettingNavEvents.ThemeChanged -> router.setThemeMode(event.mode)
        }
    }
}
