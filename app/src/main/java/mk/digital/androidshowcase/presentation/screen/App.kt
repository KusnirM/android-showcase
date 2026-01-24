package mk.digital.androidshowcase.presentation.screen

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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.presentation.base.AppCallbacks
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.base.Route.HomeSection
import mk.digital.androidshowcase.presentation.base.Route.Login
import mk.digital.androidshowcase.presentation.base.Route.Register
import mk.digital.androidshowcase.presentation.base.Route.Settings
import mk.digital.androidshowcase.presentation.base.rememberNavEntryDecorators
import mk.digital.androidshowcase.presentation.base.rememberNavRouter
import mk.digital.androidshowcase.presentation.component.AppFloatingNavBar
import mk.digital.androidshowcase.presentation.component.AppSnackbarHost
import mk.digital.androidshowcase.presentation.component.FloatingNavItem
import mk.digital.androidshowcase.presentation.component.TopAppBar
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.screen.calendar.CalendarScreen
import mk.digital.androidshowcase.presentation.screen.database.DatabaseScreen
import mk.digital.androidshowcase.presentation.screen.feature.UiComponentsScreen
import mk.digital.androidshowcase.presentation.screen.home.HomeScreen
import mk.digital.androidshowcase.presentation.screen.login.LoginScreen
import mk.digital.androidshowcase.presentation.screen.networking.NetworkingScreen
import mk.digital.androidshowcase.presentation.screen.notifications.NotificationsScreen
import mk.digital.androidshowcase.presentation.screen.platformapis.PlatformApisScreen
import mk.digital.androidshowcase.presentation.screen.register.RegisterScreen
import mk.digital.androidshowcase.presentation.screen.scanner.ScannerScreen
import mk.digital.androidshowcase.presentation.screen.settings.SettingsScreen
import mk.digital.androidshowcase.presentation.screen.storage.StorageScreen

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

data class MainViewState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

private val saveStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Login.serializer())
            subclass(Register.serializer())
            subclass(HomeSection.Home.serializer())
            subclass(HomeSection.UiComponents.serializer())
            subclass(HomeSection.Networking.serializer())
            subclass(HomeSection.Storage.serializer())
            subclass(HomeSection.PlatformApis.serializer())
            subclass(HomeSection.Scanner.serializer())
            subclass(HomeSection.Database.serializer())
            subclass(HomeSection.Calendar.serializer())
            subclass(HomeSection.Notifications.serializer())
            subclass(Settings.serializer())
        }
    }
}

@Suppress("CognitiveComplexMethod")
@Composable
fun MainView(
    state: MainViewState = MainViewState(),
    appCallbacks: AppCallbacks = AppCallbacks(),
) {
    val router: NavRouter<Route> = rememberNavRouter(saveStateConfiguration, Login, appCallbacks)
    val currentRoute: Route = router.backStack.last()
    val snackbarHostState = remember { SnackbarHostState() }

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
                            entry<HomeSection.PlatformApis> { PlatformApisScreen(router) }
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
