package com.mk.androidshowcase.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.mk.androidshowcase.data.analytics.AnalyticsClient
import com.mk.androidshowcase.data.analytics.FirebaseAnalyticsClient
import com.mk.androidshowcase.data.biometric.BiometricClient
import com.mk.androidshowcase.data.biometric.BiometricClientImpl
import com.mk.androidshowcase.data.local.StorageLocalStore
import com.mk.androidshowcase.data.local.StorageLocalStoreImpl
import com.mk.androidshowcase.data.local.preferences.PersistentPreferences
import com.mk.androidshowcase.data.local.preferences.PersistentPreferencesImpl
import com.mk.androidshowcase.data.local.preferences.Preferences
import com.mk.androidshowcase.data.local.preferences.PreferencesImpl
import com.mk.androidshowcase.data.local.preferences.SessionPreferences
import com.mk.androidshowcase.data.local.preferences.SessionPreferencesImpl
import com.mk.androidshowcase.data.location.LocationClient
import com.mk.androidshowcase.data.location.LocationClientImpl
import com.mk.androidshowcase.data.repository.user.UserClient
import com.mk.androidshowcase.data.repository.user.UserClientImpl
import com.mk.androidshowcase.data.service.LocalNotificationServiceImpl
import com.mk.androidshowcase.domain.repository.LocalNotificationService
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    const val SESSION_PREFERENCES = "session"
    const val APP_PREFERENCES = "app"

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    @Named(SESSION_PREFERENCES)
    fun provideSessionPreferences(@ApplicationContext context: Context): Preferences {
        return PreferencesImpl(context, SESSION_PREFERENCES)
    }

    @Provides
    @Singleton
    @Named(APP_PREFERENCES)
    fun provideAppPreferences(@ApplicationContext context: Context): Preferences {
        return PreferencesImpl(context, APP_PREFERENCES)
    }

    @Provides
    @Singleton
    fun provideSessionPreferencesWrapper(@Named(SESSION_PREFERENCES) prefs: Preferences): SessionPreferences {
        return SessionPreferencesImpl(prefs)
    }

    @Provides
    @Singleton
    fun providePersistentPreferences(@Named(APP_PREFERENCES) prefs: Preferences): PersistentPreferences {
        return PersistentPreferencesImpl(prefs)
    }

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context): LocationClient {
        return LocationClientImpl(context)
    }

    @Provides
    @Singleton
    fun provideBiometricClient(@ApplicationContext context: Context): BiometricClient {
        return BiometricClientImpl(context)
    }

    @Provides
    @Singleton
    fun provideLocalNotificationService(@ApplicationContext context: Context): LocalNotificationService {
        return LocalNotificationServiceImpl(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsClient(impl: FirebaseAnalyticsClient): AnalyticsClient

    @Binds
    @Singleton
    abstract fun bindUserClient(impl: UserClientImpl): UserClient

    @Binds
    @Singleton
    abstract fun bindStorageLocalStore(impl: StorageLocalStoreImpl): StorageLocalStore
}
