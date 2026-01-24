package mk.digital.androidshowcase.data.di

import mk.digital.androidshowcase.data.biometric.BiometricClient
import mk.digital.androidshowcase.data.biometric.BiometricClientImpl
import mk.digital.androidshowcase.data.di.Qualifiers.session
import mk.digital.androidshowcase.data.local.StorageLocalStore
import mk.digital.androidshowcase.data.local.StorageLocalStoreImpl
import mk.digital.androidshowcase.data.local.preferences.PersistentPreferences
import mk.digital.androidshowcase.data.local.preferences.PersistentPreferencesImpl
import mk.digital.androidshowcase.data.local.preferences.Preferences
import mk.digital.androidshowcase.data.local.preferences.PreferencesImpl
import mk.digital.androidshowcase.data.local.preferences.SessionPreferences
import mk.digital.androidshowcase.data.local.preferences.SessionPreferencesImpl
import mk.digital.androidshowcase.data.location.LocationClient
import mk.digital.androidshowcase.data.location.LocationClientImpl
import mk.digital.androidshowcase.data.notification.NotificationRepositoryImpl
import mk.digital.androidshowcase.data.repository.BiometricRepositoryImpl
import mk.digital.androidshowcase.data.repository.LocationRepositoryImpl
import mk.digital.androidshowcase.data.repository.SettingsRepositoryImpl
import mk.digital.androidshowcase.data.repository.storage.StorageRepositoryImpl
import mk.digital.androidshowcase.data.repository.user.UserClient
import mk.digital.androidshowcase.data.repository.user.UserClientImpl
import mk.digital.androidshowcase.data.repository.user.UserRepositoryImpl
import mk.digital.androidshowcase.data.service.LocalNotificationServiceImpl
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.repository.LocalNotificationService
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.domain.repository.NotificationRepository
import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.repository.UserRepository
import mk.digital.androidshowcase.data.network.NetworkModule
import mk.digital.androidshowcase.data.database.AppDatabase
import mk.digital.androidshowcase.data.repository.database.AuthRepositoryImpl
import mk.digital.androidshowcase.data.repository.database.NoteRepositoryImpl
import mk.digital.androidshowcase.data.repository.DateRepositoryImpl
import mk.digital.androidshowcase.domain.repository.AuthRepository
import mk.digital.androidshowcase.domain.repository.DateRepository
import mk.digital.androidshowcase.domain.repository.NoteRepository
import mk.digital.androidshowcase.util.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


//replace with hilt
val dataModule = module {
    singleOf(::Logger)

    // Networking - Retrofit
    single { NetworkModule.userApi }

    singleOf(::UserClientImpl) { bind<UserClient>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }

    // Qualified preferences - need explicit qualifier
    single<SessionPreferences> { SessionPreferencesImpl(get(session)) }
    single<PersistentPreferences> { PersistentPreferencesImpl(get(Qualifiers.app)) }

    singleOf(::StorageLocalStoreImpl) { bind<StorageLocalStore>() }
    singleOf(::StorageRepositoryImpl) { bind<StorageRepository>() }
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
    singleOf(::LocationRepositoryImpl) { bind<LocationRepository>() }
    singleOf(::BiometricRepositoryImpl) { bind<BiometricRepository>() }
    singleOf(::DateRepositoryImpl) { bind<DateRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }

    // Room Database
    single { AppDatabase.create(androidContext()) }
    single { get<AppDatabase>().noteDao() }
    single { get<AppDatabase>().registeredUserDao() }
    singleOf(::NoteRepositoryImpl) { bind<NoteRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

    // Qualified preferences - need androidContext()
    single<Preferences>(Qualifiers.session) { PreferencesImpl(androidContext(), Qualifiers.session.value) }
    single<Preferences>(Qualifiers.app) { PreferencesImpl(androidContext(), Qualifiers.app.value) }
    // Clients - need androidContext()
    single<LocationClient> { LocationClientImpl(androidContext()) }
    single<BiometricClient> { BiometricClientImpl(androidContext()) }
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
