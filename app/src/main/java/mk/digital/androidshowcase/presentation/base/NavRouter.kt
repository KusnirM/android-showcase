package mk.digital.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.serialization.SavedStateConfiguration
import mk.digital.androidshowcase.presentation.base.router.ExternalRouter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import kotlin.reflect.KClass

interface NavRouter<T: NavKey> {
    val backStack: NavBackStack<T>
    fun navigateTo(page: T)
    fun <R : Any> navigateTo(page: T, popUpTo: KClass<R>? = null, inclusive: Boolean = false)
    fun onBack()
    fun replaceAll(page: T)
    fun openLink(url: String)
    fun dial(number: String)
    fun share(text: String)
    fun copyToClipboard(text: String)
    fun sendEmail(to: String, subject: String, body: String)
    fun openSettings()
    fun openNotificationSettings()
}

class NavRouterImpl<T : NavKey>(
    override val backStack: NavBackStack<T>,
) : NavRouter<T>, KoinComponent {

    private val externalRouter: ExternalRouter by inject()

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

    override fun replaceAll(page: T) {
        while (backStack.removeLastOrNull() != null) { }
        backStack.add(page)
    }

    override fun openLink(url: String) = externalRouter.openLink(url)

    override fun dial(number: String) = externalRouter.dial(number)

    override fun share(text: String) = externalRouter.share(text)

    override fun copyToClipboard(text: String) = externalRouter.copyToClipboard(text)

    override fun sendEmail(to: String, subject: String, body: String) = externalRouter.sendEmail(to, subject, body)

    override fun openSettings() = externalRouter.openSettings()

    override fun openNotificationSettings() = externalRouter.openNotificationSettings()
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : NavKey> rememberNavRouter(
    config: SavedStateConfiguration,
    initialRoute: T
): NavRouter<T> {
    val backStack = rememberNavBackStack(config, initialRoute)
    return remember(backStack) {
        NavRouterImpl(backStack as NavBackStack<T>)
    }
}

@Composable
fun <T : NavKey> rememberNavEntryDecorators(): List<NavEntryDecorator<T>> = listOf(
    rememberSaveableStateHolderNavEntryDecorator(),
    rememberViewModelStoreNavEntryDecorator()
)
