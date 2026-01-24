package mk.digital.androidshowcase.util

import androidx.core.os.LocaleListCompat


fun getCurrentLanguageTag(): String {
    val appLocales = AppCompatDelegate.getApplicationLocales()
    val locale = appLocales[0] ?: LocaleListCompat.getAdjustedDefault().get(0)
    return locale?.toLanguageTag() ?: "en"
}