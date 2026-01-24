package mk.digital.androidshowcase.presentation.screen.home

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
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.feature_calendar_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_calendar_title
import mk.digital.androidshowcase.shared.generated.resources.feature_database_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_database_title
import mk.digital.androidshowcase.shared.generated.resources.feature_networking_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_notifications_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_notifications_title
import mk.digital.androidshowcase.shared.generated.resources.feature_networking_title
import mk.digital.androidshowcase.shared.generated.resources.feature_platform_apis_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_platform_apis_title
import mk.digital.androidshowcase.shared.generated.resources.feature_scanner_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_scanner_title
import mk.digital.androidshowcase.shared.generated.resources.feature_storage_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_storage_title
import mk.digital.androidshowcase.shared.generated.resources.feature_ui_components_subtitle
import mk.digital.androidshowcase.shared.generated.resources.feature_ui_components_title
import org.jetbrains.compose.resources.StringResource

data class Feature(
    val id: FeatureId,
    val titleRes: StringResource,
    val subtitleRes: StringResource,
    val icon: ImageVector
)

enum class FeatureId {
    UI_COMPONENTS,
    NETWORKING,
    STORAGE,
    DATABASE,
    PLATFORM_APIS,
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
        titleRes = Res.string.feature_database_title,
        subtitleRes = Res.string.feature_database_subtitle,
        icon = Icons.Outlined.Dataset
    ),
    Feature(
        id = FeatureId.PLATFORM_APIS,
        titleRes = Res.string.feature_platform_apis_title,
        subtitleRes = Res.string.feature_platform_apis_subtitle,
        icon = Icons.Outlined.PhoneAndroid
    ),
    Feature(
        id = FeatureId.SCANNER,
        titleRes = Res.string.feature_scanner_title,
        subtitleRes = Res.string.feature_scanner_subtitle,
        icon = Icons.Outlined.QrCode2,
    ),
    Feature(
        id = FeatureId.CALENDAR,
        titleRes = Res.string.feature_calendar_title,
        subtitleRes = Res.string.feature_calendar_subtitle,
        icon = Icons.Outlined.CalendarMonth,
    ),
    Feature(
        id = FeatureId.NOTIFICATIONS,
        titleRes = Res.string.feature_notifications_title,
        subtitleRes = Res.string.feature_notifications_subtitle,
        icon = Icons.Outlined.Notifications,
    ),
)
