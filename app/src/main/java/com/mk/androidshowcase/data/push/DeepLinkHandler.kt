package com.mk.androidshowcase.data.push

import com.mk.androidshowcase.presentation.base.Route

object DeepLinkHandler {

    private const val SCHEME = "androidshowcase"

    private val routeMap: Map<String, Route> = mapOf(
        "" to Route.HomeSection.Home,
        "home" to Route.HomeSection.Home,
        "settings" to Route.Settings,
        "networking" to Route.HomeSection.Networking,
        "storage" to Route.HomeSection.Storage,
        "ui-components" to Route.HomeSection.UiComponents,
        "uicomponents" to Route.HomeSection.UiComponents,
        "apis" to Route.HomeSection.Apis,
        "scanner" to Route.HomeSection.Scanner,
        "database" to Route.HomeSection.Database,
        "calendar" to Route.HomeSection.Calendar,
        "login" to Route.Login,
        "register" to Route.Register,
    )

    fun parseDeepLink(deepLink: String): Route? {
        val trimmed = deepLink.trim()
        if (!trimmed.startsWith("$SCHEME://")) {
            return null
        }

        val path = trimmed.removePrefix("$SCHEME://").lowercase()
        return routeMap[path]
    }
}
