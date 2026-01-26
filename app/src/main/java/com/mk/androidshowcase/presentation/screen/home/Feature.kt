package com.mk.androidshowcase.presentation.screen.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Dataset
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.ui.graphics.vector.ImageVector
import com.mk.androidshowcase.R

data class Feature(
    val id: FeatureId,
    @get:StringRes val titleRes: Int,
    @get:StringRes val subtitleRes: Int,
    val icon: ImageVector
)

enum class FeatureId {
    UI_COMPONENTS,
    NETWORKING,
    STORAGE,
    DATABASE,
    APIS,
    SCANNER,
    CALENDAR,
    NOTIFICATIONS,
}

val showcaseFeatures = listOf(
    Feature(
        id = FeatureId.UI_COMPONENTS,
        titleRes = R.string.feature_ui_components_title,
        subtitleRes = R.string.feature_ui_components_subtitle,
        icon = Icons.Outlined.Palette
    ),
    Feature(
        id = FeatureId.NETWORKING,
        titleRes = R.string.feature_networking_title,
        subtitleRes = R.string.feature_networking_subtitle,
        icon = Icons.Outlined.Cloud
    ),
    Feature(
        id = FeatureId.STORAGE,
        titleRes = R.string.feature_storage_title,
        subtitleRes = R.string.feature_storage_subtitle,
        icon = Icons.Outlined.Storage
    ),
    Feature(
        id = FeatureId.DATABASE,
        titleRes = R.string.feature_database_title,
        subtitleRes = R.string.feature_database_subtitle,
        icon = Icons.Outlined.Dataset
    ),
    Feature(
        id = FeatureId.APIS,
        titleRes = R.string.feature_apis_title,
        subtitleRes = R.string.feature_apis_subtitle,
        icon = Icons.Outlined.PhoneAndroid
    ),
    Feature(
        id = FeatureId.SCANNER,
        titleRes = R.string.feature_scanner_title,
        subtitleRes = R.string.feature_scanner_subtitle,
        icon = Icons.Outlined.QrCode2,
    ),
    Feature(
        id = FeatureId.CALENDAR,
        titleRes = R.string.feature_calendar_title,
        subtitleRes = R.string.feature_calendar_subtitle,
        icon = Icons.Outlined.CalendarMonth,
    ),
    Feature(
        id = FeatureId.NOTIFICATIONS,
        titleRes = R.string.feature_notifications_title,
        subtitleRes = R.string.feature_notifications_subtitle,
        icon = Icons.Outlined.Notifications,
    ),
)
