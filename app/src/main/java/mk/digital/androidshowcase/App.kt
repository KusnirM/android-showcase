package mk.digital.androidshowcase

import android.app.Application
import com.google.firebase.FirebaseApp
import mk.digital.androidshowcase.data.di.dataModule
import mk.digital.androidshowcase.di.domainModule

import mk.digital.androidshowcase.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initFb()
    }

    private fun initKoin() {
        startKoin {
            modules(
                presentationModule,
                domainModule,
                dataModule
            )
            androidLogger()
            androidContext(this@App)
        }

    }

    private fun initFb() {
        FirebaseApp.initializeApp(this)
        initFBAppCheck()
    }

    private fun initFBAppCheck() {
        AppCheckInitializer.init()
    }
}
