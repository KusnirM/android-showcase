package mk.digital.androidshowcase.data.di

import io.ktor.client.HttpClient
import mk.digital.androidshowcase.data.database.AppDatabase
import mk.digital.androidshowcase.data.database.DatabaseDriverFactory
import mk.digital.androidshowcase.data.local.StorageLocalStore
import mk.digital.androidshowcase.data.local.StorageLocalStoreImpl
import mk.digital.androidshowcase.data.local.preferences.PersistentPreferences
import mk.digital.androidshowcase.data.local.preferences.PersistentPreferencesImpl
import mk.digital.androidshowcase.data.local.preferences.SessionPreferences
import mk.digital.androidshowcase.data.local.preferences.SessionPreferencesImpl
import mk.digital.androidshowcase.data.network.HttpClientProvider
import mk.digital.androidshowcase.data.repository.BiometricRepositoryImpl
import mk.digital.androidshowcase.data.repository.DateRepositoryImpl
import mk.digital.androidshowcase.data.repository.LocationRepositoryImpl
import mk.digital.androidshowcase.data.repository.SettingsRepositoryImpl
import mk.digital.androidshowcase.data.notification.NotificationRepositoryImpl
import mk.digital.androidshowcase.data.repository.database.AuthRepositoryImpl
import mk.digital.androidshowcase.data.repository.database.NoteRepositoryImpl
import mk.digital.androidshowcase.data.repository.storage.StorageRepositoryImpl
import mk.digital.androidshowcase.data.repository.user.UserClient
import mk.digital.androidshowcase.data.repository.user.UserClientImpl
import mk.digital.androidshowcase.data.repository.user.UserRepositoryImpl
import mk.digital.androidshowcase.di.Qualifiers.app
import mk.digital.androidshowcase.di.Qualifiers.session
import mk.digital.androidshowcase.domain.repository.AuthRepository
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.repository.DateRepository
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.domain.repository.NoteRepository
import mk.digital.androidshowcase.domain.repository.NotificationRepository
import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.repository.UserRepository
import mk.digital.androidshowcase.util.Logger
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


//replace with hilt
val dataModule = module {
    singleOf(::Logger)
    singleOf(::UserClientImpl) { bind<UserClient>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    single { provideHttpClient() }

    // Qualified preferences - need explicit qualifier
    single<SessionPreferences> { SessionPreferencesImpl(get(session)) }
    single<PersistentPreferences> { PersistentPreferencesImpl(get(app)) }

    singleOf(::StorageLocalStoreImpl) { bind<StorageLocalStore>() }
    singleOf(::StorageRepositoryImpl) { bind<StorageRepository>() }
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
    singleOf(::LocationRepositoryImpl) { bind<LocationRepository>() }
    singleOf(::BiometricRepositoryImpl) { bind<BiometricRepository>() }
    singleOf(::DateRepositoryImpl) { bind<DateRepository>() }
    singleOf(::NoteRepositoryImpl) { bind<NoteRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }

    // Database - needs special factory
    single { AppDatabase(get<DatabaseDriverFactory>().createDriver()) }
}

fun provideHttpClient(): HttpClient = HttpClientProvider().create()
