package mk.digital.androidshowcase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mk.digital.androidshowcase.data.database.dao.NoteDao
import mk.digital.androidshowcase.data.database.dao.RegisteredUserDao
import mk.digital.androidshowcase.data.database.entity.NoteEntity
import mk.digital.androidshowcase.data.database.entity.RegisteredUserEntity

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
