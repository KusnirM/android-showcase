package mk.digital.androidshowcase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mk.digital.androidshowcase.data.repository.BiometricRepositoryImpl
import mk.digital.androidshowcase.data.repository.DateRepositoryImpl
import mk.digital.androidshowcase.data.repository.LocationRepositoryImpl
import mk.digital.androidshowcase.data.repository.SettingsRepositoryImpl
import mk.digital.androidshowcase.data.repository.database.AuthRepositoryImpl
import mk.digital.androidshowcase.data.repository.database.NoteRepositoryImpl
import mk.digital.androidshowcase.data.repository.storage.StorageRepositoryImpl
import mk.digital.androidshowcase.data.repository.user.UserRepositoryImpl
import mk.digital.androidshowcase.data.notification.NotificationRepositoryImpl
import mk.digital.androidshowcase.data.notification.PushNotificationServiceImpl
import mk.digital.androidshowcase.domain.repository.AuthRepository
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.repository.DateRepository
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.domain.repository.NoteRepository
import mk.digital.androidshowcase.domain.repository.NotificationRepository
import mk.digital.androidshowcase.domain.repository.PushNotificationService
import mk.digital.androidshowcase.domain.repository.SettingsRepository
import mk.digital.androidshowcase.domain.repository.StorageRepository
import mk.digital.androidshowcase.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindStorageRepository(impl: StorageRepositoryImpl): StorageRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindBiometricRepository(impl: BiometricRepositoryImpl): BiometricRepository

    @Binds
    @Singleton
    abstract fun bindDateRepository(impl: DateRepositoryImpl): DateRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindPushNotificationService(impl: PushNotificationServiceImpl): PushNotificationService
}
