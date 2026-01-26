package com.mk.androidshowcase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mk.androidshowcase.data.database.dao.NoteDao
import com.mk.androidshowcase.data.database.dao.RegisteredUserDao
import com.mk.androidshowcase.data.database.entity.NoteEntity
import com.mk.androidshowcase.data.database.entity.RegisteredUserEntity

@Database(
    entities = [NoteEntity::class, RegisteredUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun registeredUserDao(): RegisteredUserDao

    companion object {
        private const val DATABASE_NAME = "app.db"

        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
