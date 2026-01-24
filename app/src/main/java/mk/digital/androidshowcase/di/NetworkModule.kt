package mk.digital.androidshowcase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mk.digital.androidshowcase.data.network.NetworkModule as AppNetworkModule
import mk.digital.androidshowcase.data.network.UserApi
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
