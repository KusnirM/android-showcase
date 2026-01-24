package mk.digital.androidshowcase.data.push

import mk.digital.androidshowcase.presentation.base.Route

object DeepLinkHandler {

    private const val SCHEME = "kmpshowcase"

    fun parseDeepLink(deepLink: String): Route? {
        if (!deepLink.startsWith("$SCHEME://")) {
            return null
        }

        val path = deepLink.removePrefix("$SCHEME://").lowercase()

        return when {
            path == "home" || path.isEmpty() -> Route.HomeSection.Home
            path == "settings" -> Route.Settings
            path == "networking" -> Route.HomeSection.Networking
            path == "storage" -> Route.HomeSection.Storage
            path == "ui-components" || path == "uicomponents" -> Route.HomeSection.UiComponents
            path == "apis" -> Route.HomeSection.Apis
            path == "scanner" -> Route.HomeSection.Scanner
            path == "database" -> Route.HomeSection.Database
            path == "calendar" -> Route.HomeSection.Calendar
            path == "login" -> Route.Login
            path == "register" -> Route.Register
            else -> null
        }
    }
}
