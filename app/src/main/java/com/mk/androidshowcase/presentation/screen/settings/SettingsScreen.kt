package com.mk.androidshowcase.presentation.screen.settings

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import com.mk.androidshowcase.R
import com.mk.androidshowcase.presentation.base.CollectNavEvents
import com.mk.androidshowcase.presentation.base.NavEvent
import com.mk.androidshowcase.presentation.base.NavRouter
import com.mk.androidshowcase.presentation.base.Route
import com.mk.androidshowcase.presentation.base.lifecycleAwareViewModel
import com.mk.androidshowcase.presentation.component.AppAlertDialog
import com.mk.androidshowcase.presentation.component.AppRadioButton
import com.mk.androidshowcase.presentation.component.AvatarState
import com.mk.androidshowcase.presentation.component.AvatarView
import com.mk.androidshowcase.presentation.component.buttons.AppTextButtonError
import com.mk.androidshowcase.presentation.component.cards.AppElevatedCard
import com.mk.androidshowcase.presentation.component.image.AppIconPrimary
import com.mk.androidshowcase.presentation.component.imagepicker.ImagePickerView
import com.mk.androidshowcase.presentation.component.imagepicker.ImagePickerViewModel
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral100
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargePrimary
import com.mk.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import com.mk.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import com.mk.androidshowcase.presentation.component.text.titleLarge.TextTitleLargePrimary
import com.mk.androidshowcase.presentation.foundation.AppTheme
import com.mk.androidshowcase.presentation.foundation.floatingNavBarSpace
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun SettingsScreen(
    router: NavRouter<Route>,
    viewModel: SettingsViewModel = lifecycleAwareViewModel(),
    imagePickerViewModel: ImagePickerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val imagePickerState by imagePickerViewModel.state.collectAsStateWithLifecycle()

    val avatarState = when {
        imagePickerState.isLoading -> AvatarState.Loading
        imagePickerState.imageBitmap != null -> AvatarState.Loaded(imagePickerState.imageBitmap!!)
        else -> AvatarState.Empty
    }

    SettingsNavEvents(router, viewModel.navEvent)
    SettingsScreen(
        state = state,
        avatarState = avatarState,
        onProfilePhotoClick = imagePickerViewModel::showDialog,
        onThemeClick = viewModel::showThemeDialog,
        onThemeSelected = viewModel::setThemeMode,
        onThemeDismiss = viewModel::hideThemeDialog,
        onLanguageSelected = viewModel::onLanguageSelected,
        onCrashClick = viewModel::triggerTestCrash,
        onLogout = viewModel::logout
    )

    ImagePickerView(viewModel = imagePickerViewModel)
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    avatarState: AvatarState = AvatarState.Empty,
    onProfilePhotoClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onThemeSelected: (ThemeModeState) -> Unit = {},
    onThemeDismiss: () -> Unit = {},
    onLanguageSelected: (LanguageState) -> Unit = {},
    onCrashClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
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
                onClick = onProfilePhotoClick
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
                onClick = onThemeClick
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
                onLanguageSelected = onLanguageSelected
            )
        }

        if (state.showCrashButton) {
            item {
                AppElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCrashClick
                ) {
                    SettingsItem(
                        icon = {
                            AppIconPrimary(
                                Icons.Outlined.BugReport,
                                contentDescription = stringResource(R.string.settings_test_crash_title)
                            )
                        },
                        title = stringResource(R.string.settings_test_crash_title),
                        value = stringResource(R.string.settings_test_crash_subtitle)
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
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (state.showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = state.themeModeState,
            onThemeSelected = { themeModeState ->
                onThemeSelected(themeModeState)
                onThemeDismiss()
            },
            onDismiss = onThemeDismiss
        )
    }
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
            is SettingNavEvents.Logout -> router.navigateTo(
                Route.Login,
                popUpTo = Route.HomeSection.Home::class,
                inclusive = true
            )

            is SettingNavEvents.ThemeChanged -> router.setThemeMode(event.mode)
        }
    }
}

@Preview
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview(
    @PreviewParameter(SettingsScreenPreviewParams::class) state: SettingsState
) {
    AppTheme {
        SettingsScreen(state = state)
    }
}

internal class SettingsScreenPreviewParams : PreviewParameterProvider<SettingsState> {
    override val values = sequenceOf(
        SettingsState(),
        SettingsState(themeModeState = ThemeModeState.DARK, currentLanguage = LanguageState.SK),
        SettingsState(showThemeDialog = true)
    )
}
