package com.mk.androidshowcase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.mk.androidshowcase.data.database.AppDatabase
import com.mk.androidshowcase.data.database.dao.NoteDao
import com.mk.androidshowcase.data.database.dao.RegisteredUserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideRegisteredUserDao(database: AppDatabase): RegisteredUserDao {
        return database.registeredUserDao()
    }
}
