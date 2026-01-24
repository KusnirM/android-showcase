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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.settings.GetThemeModeUseCase
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.base.Route.HomeSection
import mk.digital.androidshowcase.presentation.base.Route.Login
import mk.digital.androidshowcase.presentation.base.Route.Register
import mk.digital.androidshowcase.presentation.base.Route.Settings
import mk.digital.androidshowcase.presentation.base.WithViewModel
import mk.digital.androidshowcase.presentation.base.rememberNavEntryDecorators
import mk.digital.androidshowcase.presentation.base.rememberNavRouter
import mk.digital.androidshowcase.presentation.component.AppFloatingNavBar
import mk.digital.androidshowcase.presentation.component.AppSnackbarHost
import mk.digital.androidshowcase.presentation.component.FloatingNavItem
import mk.digital.androidshowcase.presentation.component.TopAppBar
import mk.digital.androidshowcase.presentation.component.imagepicker.ImagePickerViewModel
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import mk.digital.androidshowcase.presentation.foundation.ThemeMode
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.screen.calendar.CalendarScreen
import mk.digital.androidshowcase.presentation.screen.calendar.CalendarViewModel
import mk.digital.androidshowcase.presentation.screen.database.DatabaseScreen
import mk.digital.androidshowcase.presentation.screen.database.DatabaseViewModel
import mk.digital.androidshowcase.presentation.screen.feature.UiComponentsScreen
import mk.digital.androidshowcase.presentation.screen.home.HomeNavEvents
import mk.digital.androidshowcase.presentation.screen.home.HomeScreen
import mk.digital.androidshowcase.presentation.screen.home.HomeViewModel
import mk.digital.androidshowcase.presentation.screen.login.LoginNavEvents
import mk.digital.androidshowcase.presentation.screen.login.LoginScreen
import mk.digital.androidshowcase.presentation.screen.login.LoginViewModel
import mk.digital.androidshowcase.presentation.screen.networking.NetworkingScreen
import mk.digital.androidshowcase.presentation.screen.networking.NetworkingViewModel
import mk.digital.androidshowcase.presentation.screen.notifications.NotificationsNavEvents
import mk.digital.androidshowcase.presentation.screen.notifications.NotificationsScreen
import mk.digital.androidshowcase.presentation.screen.notifications.NotificationsViewModel
import mk.digital.androidshowcase.presentation.screen.platformapis.PlatformApisNavEvents
import mk.digital.androidshowcase.presentation.screen.platformapis.PlatformApisScreen
import mk.digital.androidshowcase.presentation.screen.platformapis.PlatformApisViewModel
import mk.digital.androidshowcase.presentation.screen.register.RegisterNavEvents
import mk.digital.androidshowcase.presentation.screen.register.RegisterScreen
import mk.digital.androidshowcase.presentation.screen.register.RegisterViewModel
import mk.digital.androidshowcase.presentation.screen.scanner.ScannerScreen
import mk.digital.androidshowcase.presentation.screen.scanner.ScannerViewModel
import mk.digital.androidshowcase.presentation.screen.settings.SettingsNavEvents
import mk.digital.androidshowcase.presentation.screen.settings.SettingsScreen
import mk.digital.androidshowcase.presentation.screen.settings.SettingsViewModel
import mk.digital.androidshowcase.presentation.screen.storage.StorageScreen
import mk.digital.androidshowcase.presentation.screen.storage.StorageViewModel
import org.koin.compose.koinInject

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}

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
    onSetLocale: ((String) -> Unit)? = null,
    onOpenSettings: (() -> Unit)? = null,
) {
    val router: NavRouter<Route> = rememberNavRouter(saveStateConfiguration, Login)
    val currentRoute: Route = router.backStack.last()
    val snackbarHostState = remember { SnackbarHostState() }

    val getThemeModeUseCase = koinInject<GetThemeModeUseCase>()
    var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }

    LaunchedEffect(Unit) {
        themeMode = getThemeModeUseCase()
    }

    AppTheme(themeMode = themeMode) {
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
                            entry<Login> {
                                WithViewModel<LoginViewModel> { viewModel ->
                                    LoginNavEvents(viewModel, router)
                                    LoginScreen(viewModel)
                                }
                            }
                            entry<Register> {
                                WithViewModel<RegisterViewModel> { viewModel ->
                                    RegisterNavEvents(viewModel, router)
                                    RegisterScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Home> {
                                WithViewModel<HomeViewModel> { viewModel ->
                                    HomeNavEvents(viewModel, router)
                                    HomeScreen(viewModel)
                                }
                            }
                            entry<HomeSection.UiComponents> { UiComponentsScreen() }
                            entry<HomeSection.Networking> {
                                WithViewModel<NetworkingViewModel> { viewModel ->
                                    NetworkingScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Storage> {
                                WithViewModel<StorageViewModel> { viewModel ->
                                    StorageScreen(viewModel)
                                }
                            }
                            entry<HomeSection.PlatformApis> {
                                WithViewModel<PlatformApisViewModel> { viewModel ->
                                    PlatformApisNavEvents(viewModel, router)
                                    PlatformApisScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Scanner> {
                                WithViewModel<ScannerViewModel> { viewModel ->
                                    ScannerScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Database> {
                                WithViewModel<DatabaseViewModel> { viewModel ->
                                    DatabaseScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Calendar> {
                                WithViewModel<CalendarViewModel> { viewModel ->
                                    CalendarScreen(viewModel)
                                }
                            }
                            entry<HomeSection.Notifications> {
                                WithViewModel<NotificationsViewModel> { viewModel ->
                                    NotificationsNavEvents(viewModel, router)
                                    NotificationsScreen(viewModel)
                                }
                            }
                            entry<Settings> {
                                WithViewModel<SettingsViewModel> { viewModel ->
                                    WithViewModel<ImagePickerViewModel> { imagePickerViewModel ->
                                        SettingsNavEvents(
                                            viewModel = viewModel,
                                            router = router,
                                            onSetLocale = onSetLocale,
                                            onOpenSettings = onOpenSettings,
                                            onThemeChanged = { mode -> themeMode = mode }
                                        )
                                        SettingsScreen(viewModel, imagePickerViewModel)
                                    }
                                }
                            }
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
