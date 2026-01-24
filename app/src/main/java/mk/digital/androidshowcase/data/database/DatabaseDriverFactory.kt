package mk.digital.androidshowcase.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver

const val DATABASE_NAME = "app.db"

 class DatabaseDriverFactory(private val context: Context) {
     fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, DATABASE_NAME)
    }
}
