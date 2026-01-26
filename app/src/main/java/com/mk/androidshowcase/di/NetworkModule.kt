package com.mk.androidshowcase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mk.androidshowcase.data.network.NetworkModule as AppNetworkModule
import com.mk.androidshowcase.data.network.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return AppNetworkModule.userApi
    }
}
