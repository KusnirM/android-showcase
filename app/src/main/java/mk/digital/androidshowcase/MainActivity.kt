package mk.digital.androidshowcase

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import mk.digital.androidshowcase.data.service.LocalNotificationServiceImpl
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.presentation.base.AppCallbacks
import mk.digital.androidshowcase.presentation.base.router.ExternalRouter
import mk.digital.androidshowcase.presentation.screen.MainView
import mk.digital.androidshowcase.presentation.screen.MainViewState
import mk.digital.androidshowcase.presentation.screen.main.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var externalRouter: ExternalRouter

    @Inject
    lateinit var pushService: PushNotificationService


    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainState by mainViewModel.state.collectAsStateWithLifecycle()
            MainView(
                state = MainViewState(themeMode = mainState.themeMode),
                appCallbacks = AppCallbacks(
                    openLink = externalRouter::openLink,
                    dial = externalRouter::dial,
                    share = externalRouter::share,
                    copyToClipboard = externalRouter::copyToClipboard,
                    sendEmail = externalRouter::sendEmail,
                    openSettings = externalRouter::openSettings,
                    openNotificationSettings = externalRouter::openNotificationSettings,
                    setLocale = { tag ->
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
                    },
                    setThemeMode = mainViewModel::setThemeMode
                )
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLinkIntent(intent)
    }

    private fun handleDeepLinkIntent(intent: Intent?) {
        intent?.getStringExtra(LocalNotificationServiceImpl.EXTRA_DEEP_LINK)?.let { deepLink ->
            pushService.onDeepLinkReceived(deepLink)
        }
    }
}

