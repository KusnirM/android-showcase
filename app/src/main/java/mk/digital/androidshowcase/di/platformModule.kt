package mk.digital.androidshowcase.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named

val platformModule: Module = module {
    singleOf(::ExternalRouter)

    // Qualified preferences - need androidContext()
    single<Preferences>(session) { PreferencesImpl(androidContext(), session.value) }
    single<Preferences>(app) { PreferencesImpl(androidContext(), app.value) }

    // Platform clients - need androidContext()
    single<LocationClient> { LocationClientImpl(androidContext()) }
    single<BiometricClient> { BiometricClientImpl(androidContext()) }
    single { DatabaseDriverFactory(androidContext()) }

    single<LocalNotificationService> { LocalNotificationServiceImpl(androidContext()) }
}

object Qualifiers {
    val session = named(PreferencesScope.SESSION.value)
    val app = named(PreferencesScope.APP.value)
}

enum class PreferencesScope(val value: String) {
    SESSION("session"),
    APP("app")
}
