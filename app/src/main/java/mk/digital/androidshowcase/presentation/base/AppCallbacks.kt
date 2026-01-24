package mk.digital.androidshowcase.presentation.base

import mk.digital.androidshowcase.presentation.foundation.ThemeMode

data class AppCallbacks(
    // Navigation
    val navigateTo: (Route) -> Unit = {},
    val onBack: () -> Unit = {},
    val replaceAll: (Route) -> Unit = {},
    // External actions
    val openLink: (String) -> Unit = {},
    val dial: (String) -> Unit = {},
    val share: (String) -> Unit = {},
    val copyToClipboard: (String) -> Unit = {},
    val sendEmail: (to: String, subject: String, body: String) -> Unit = { _, _, _ -> },
    val openSettings: () -> Unit = {},
    val openNotificationSettings: () -> Unit = {},
    // App settings
    val setLocale: (String) -> Unit = {},
    val setThemeMode: (ThemeMode) -> Unit = {},
)
