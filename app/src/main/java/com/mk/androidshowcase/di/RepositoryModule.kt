package com.mk.androidshowcase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mk.androidshowcase.data.repository.BiometricRepositoryImpl
import com.mk.androidshowcase.data.repository.DateRepositoryImpl
import com.mk.androidshowcase.data.repository.LocationRepositoryImpl
import com.mk.androidshowcase.data.repository.SettingsRepositoryImpl
import com.mk.androidshowcase.data.repository.database.AuthRepositoryImpl
import com.mk.androidshowcase.data.repository.database.NoteRepositoryImpl
import com.mk.androidshowcase.data.repository.storage.StorageRepositoryImpl
import com.mk.androidshowcase.data.repository.user.UserRepositoryImpl
import com.mk.androidshowcase.data.notification.NotificationRepositoryImpl
import com.mk.androidshowcase.data.notification.PushNotificationServiceImpl
import com.mk.androidshowcase.domain.repository.AuthRepository
import com.mk.androidshowcase.domain.repository.BiometricRepository
import com.mk.androidshowcase.domain.repository.DateRepository
import com.mk.androidshowcase.domain.repository.LocationRepository
import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.repository.NotificationRepository
import com.mk.androidshowcase.domain.repository.PushNotificationService
import com.mk.androidshowcase.domain.repository.SettingsRepository
import com.mk.androidshowcase.domain.repository.StorageRepository
import com.mk.androidshowcase.domain.repository.UserRepository
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
