package com.mk.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.mk.androidshowcase.presentation.base.Route.HomeSection
import com.mk.androidshowcase.presentation.base.Route.Login
import com.mk.androidshowcase.presentation.base.Route.Register
import com.mk.androidshowcase.presentation.base.Route.Settings
import com.mk.androidshowcase.presentation.foundation.ThemeMode
import kotlin.reflect.KClass

interface NavRouter<T : NavKey> {
    val backStack: NavBackStack<T>

    // Navigation
    fun navigateTo(page: T)
    fun <R : Any> navigateTo(page: T, popUpTo: KClass<R>? = null, inclusive: Boolean = false)
    fun onBack()

    // External actions
    fun openLink(url: String)
    fun dial(number: String)
    fun share(text: String)
    fun copyToClipboard(text: String)
    fun sendEmail(to: String, subject: String, body: String)
    fun openNotificationSettings()

    // App settings
    fun setLocale(tag: String)
    fun setThemeMode(mode: ThemeMode)
}

class NavRouterImpl<T : NavKey>(
    override val backStack: NavBackStack<T>,
    private val callbacks: AppCallbacks,
) : NavRouter<T> {

    override fun navigateTo(page: T) {
        backStack.add(page)
    }

    override fun <R : Any> navigateTo(page: T, popUpTo: KClass<R>?, inclusive: Boolean) {
        if (popUpTo != null) {
            while (backStack.lastOrNull()?.let { !popUpTo.isInstance(it) } == true) {
                backStack.removeLastOrNull()
            }
            if (inclusive) {
                backStack.removeLastOrNull()
            }
        }
        backStack.add(page)
    }

    override fun onBack() {
        backStack.removeLastOrNull()
    }

    override fun openLink(url: String) = callbacks.openLink(url)
    override fun dial(number: String) = callbacks.dial(number)
    override fun share(text: String) = callbacks.share(text)
    override fun copyToClipboard(text: String) = callbacks.copyToClipboard(text)
    override fun sendEmail(to: String, subject: String, body: String) = callbacks.sendEmail(to, subject, body)
    override fun openNotificationSettings() = callbacks.openNotificationSettings()
    override fun setLocale(tag: String) = callbacks.setLocale(tag)
    override fun setThemeMode(mode: ThemeMode) = callbacks.setThemeMode(mode)
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : NavKey> rememberNavRouter(
    appCallbacks: AppCallbacks = AppCallbacks(),
): NavRouter<T> {
    val backStack = rememberNavBackStack(saveStateConfiguration, Login)
    return remember(backStack, appCallbacks) {
        NavRouterImpl(backStack as NavBackStack<T>, appCallbacks)
    }
}

@Composable
fun <T : NavKey> rememberNavEntryDecorators(): List<NavEntryDecorator<T>> = listOf(
    rememberSaveableStateHolderNavEntryDecorator(),
    rememberViewModelStoreNavEntryDecorator()
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
            subclass(HomeSection.Apis.serializer())
            subclass(HomeSection.Scanner.serializer())
            subclass(HomeSection.Database.serializer())
            subclass(HomeSection.Calendar.serializer())
            subclass(HomeSection.Notifications.serializer())
            subclass(Settings.serializer())
        }
    }
}
