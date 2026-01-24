package mk.digital.androidshowcase.presentation.base

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import mk.digital.androidshowcase.R

@Serializable
sealed interface Route : NavKey {
    @get:StringRes
    val titleRes: Int
    val showBackArrow: Boolean get() = true
    val showTopBar: Boolean get() = true
    val showBottomNav: Boolean get() = true

    @Serializable
    data object Login : Route {
        override val titleRes = R.string.screen_login
        override val showBackArrow = false
        override val showTopBar = false
        override val showBottomNav = false
    }

    @Serializable
    data object Register : Route {
        override val titleRes = R.string.screen_register
        override val showBackArrow = true
        override val showTopBar = false
        override val showBottomNav = false
    }

    @Serializable
    sealed interface HomeSection : Route {
        @Serializable
        data object Home : HomeSection {
            override val titleRes = R.string.screen_home
            override val showBackArrow = false
        }

        @Serializable
        data object UiComponents : HomeSection {
            override val titleRes = R.string.screen_ui_components
        }

        @Serializable
        data object Networking : HomeSection {
            override val titleRes = R.string.screen_networking
        }

        @Serializable
        data object Storage : HomeSection {
            override val titleRes = R.string.screen_storage
        }

        @Serializable
        data object PlatformApis : HomeSection {
            override val titleRes = R.string.screen_platform_apis
        }

        @Serializable
        data object Scanner : HomeSection {
            override val titleRes = R.string.screen_scanner
        }

        @Serializable
        data object Database : HomeSection {
            override val titleRes = R.string.screen_database
        }

        @Serializable
        data object Calendar : HomeSection {
            override val titleRes = R.string.screen_calendar
        }

        @Serializable
        data object Notifications : HomeSection {
            override val titleRes = R.string.screen_notifications
        }
    }

    @Serializable
    data object Settings : Route {
        override val titleRes = R.string.screen_settings
        override val showBackArrow = false
    }
}
