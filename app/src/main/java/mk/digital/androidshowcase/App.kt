package mk.digital.androidshowcase

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initFb()
    }

    private fun initFb() {
        FirebaseApp.initializeApp(this)
        AppCheckInitializer.init()
    }
}
