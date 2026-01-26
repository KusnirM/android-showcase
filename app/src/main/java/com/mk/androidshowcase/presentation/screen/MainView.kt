package com.mk.androidshowcase.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.R
import com.mk.androidshowcase.data.push.DeepLinkHandler
import com.mk.androidshowcase.presentation.base.AppCallbacks
import com.mk.androidshowcase.presentation.base.NavRouter
import com.mk.androidshowcase.presentation.base.Route
import com.mk.androidshowcase.presentation.base.Route.HomeSection
import com.mk.androidshowcase.presentation.base.Route.Login
import com.mk.androidshowcase.presentation.base.Route.Register
import com.mk.androidshowcase.presentation.base.Route.Settings
import com.mk.androidshowcase.presentation.base.rememberNavEntryDecorators
import com.mk.androidshowcase.presentation.base.rememberNavRouter
import com.mk.androidshowcase.presentation.component.AppFloatingNavBar
import com.mk.androidshowcase.presentation.component.AppSnackbarHost
import com.mk.androidshowcase.presentation.component.FloatingNavItem
import com.mk.androidshowcase.presentation.component.TopAppBar
import com.mk.androidshowcase.presentation.foundation.AppTheme
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import com.mk.androidshowcase.presentation.foundation.floatingNavBarSpace
import com.mk.androidshowcase.presentation.foundation.space4
import com.mk.androidshowcase.presentation.screen.apis.ApisScreen
import com.mk.androidshowcase.presentation.screen.calendar.CalendarScreen
import com.mk.androidshowcase.presentation.screen.database.DatabaseScreen
import com.mk.androidshowcase.presentation.screen.feature.UiComponentsScreen
import com.mk.androidshowcase.presentation.screen.home.HomeScreen
import com.mk.androidshowcase.presentation.screen.login.LoginScreen
import com.mk.androidshowcase.presentation.screen.networking.NetworkingScreen
import com.mk.androidshowcase.presentation.screen.notifications.NotificationsScreen
import com.mk.androidshowcase.presentation.screen.register.RegisterScreen
import com.mk.androidshowcase.presentation.screen.scanner.ScannerScreen
import com.mk.androidshowcase.presentation.screen.settings.SettingsScreen
import com.mk.androidshowcase.presentation.screen.storage.StorageScreen

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

data class MainViewState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

@Suppress("CognitiveComplexMethod")
@Composable
fun MainView(
    state: MainViewState = MainViewState(),
    appCallbacks: AppCallbacks = AppCallbacks(),
    deepLinks: Flow<String>,
) {
    val router: NavRouter<Route> = rememberNavRouter(appCallbacks)
    val currentRoute: Route = router.backStack.last()
    val snackbarHostState = remember { SnackbarHostState() }

    DeepLinkEffect(router, deepLinks)

    AppTheme(themeMode = state.themeMode) {
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    snackbarHost = {
                        AppSnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.padding(bottom = floatingNavBarSpace)
                        )
                    },
                    contentWindowInsets = WindowInsets(0),
                    topBar = {
                        if (currentRoute.showTopBar) {
                            TopAppBar(
                                title = stringResource(currentRoute.titleRes),
                                navIcon = if (currentRoute.showBackArrow) Icons.AutoMirrored.Filled.ArrowBack else null,
                                backClick = router::onBack,
                            )
                        }
                    },
                ) { contentPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(contentPadding),
                        backStack = router.backStack,
                        onBack = router::onBack,
                        entryDecorators = rememberNavEntryDecorators(),
                        entryProvider = entryProvider {
                            entry<Login> { LoginScreen(router) }
                            entry<Register> { RegisterScreen(router) }
                            entry<HomeSection.Home> { HomeScreen(router) }
                            entry<HomeSection.UiComponents> { UiComponentsScreen() }
                            entry<HomeSection.Networking> { NetworkingScreen() }
                            entry<HomeSection.Storage> { StorageScreen() }
                            entry<HomeSection.Apis> { ApisScreen(router) }
                            entry<HomeSection.Scanner> { ScannerScreen() }
                            entry<HomeSection.Database> { DatabaseScreen() }
                            entry<HomeSection.Calendar> { CalendarScreen() }
                            entry<HomeSection.Notifications> { NotificationsScreen(router) }
                            entry<Settings> { SettingsScreen(router) }
                        }
                    )
                }

                AnimatedVisibility(
                    visible = currentRoute.showBottomNav,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .systemBarsPadding()
                        .padding(bottom = space4)
                ) {
                    AppFloatingNavBar(
                        items = listOf(
                            FloatingNavItem(
                                icon = Icons.Filled.Home,
                                label = stringResource(R.string.nav_home),
                                selected = currentRoute is HomeSection,
                                onClick = {
                                    if (currentRoute !is HomeSection.Home) {
                                        router.navigateTo(
                                            page = HomeSection.Home,
                                            popUpTo = HomeSection.Home::class,
                                            inclusive = true
                                        )
                                    }
                                }
                            ),
                            FloatingNavItem(
                                icon = Icons.Filled.Settings,
                                label = stringResource(R.string.nav_settings),
                                selected = currentRoute is Settings,
                                onClick = {
                                    if (currentRoute !is Settings) {
                                        router.navigateTo(
                                            page = Settings,
                                            popUpTo = HomeSection.Home::class,
                                            inclusive = false
                                        )
                                    }
                                }
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun DeepLinkEffect(
    router: NavRouter<Route>,
    deepLinks: Flow<String>,
) {
    LaunchedEffect(Unit) {
        deepLinks.collect { deepLink ->
            DeepLinkHandler.parseDeepLink(deepLink)?.let { route ->
                when (route) {
                    is HomeSection -> if (route != HomeSection.Home) {
                        router.navigateTo(route, popUpTo = HomeSection.Home::class)
                    }

                    is Settings -> router.navigateTo(route, popUpTo = HomeSection.Home::class)

                    Login,
                    Register -> router.navigateTo(route, popUpTo = HomeSection.Home::class, inclusive = true)
                }
            }
        }
    }
}
