package com.mk.androidshowcase.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.first

interface Preferences {
    val storageName: String

    suspend fun putString(key: String, value: String?)
    suspend fun getString(key: String): String?

    suspend fun putBoolean(key: String, value: Boolean?)
    suspend fun getBoolean(key: String): Boolean?

    suspend fun putInt(key: String, value: Int?)
    suspend fun getInt(key: String): Int?

    suspend fun putFloat(key: String, value: Float?)
    suspend fun getFloat(key: String): Float?

    suspend fun putLong(key: String, value: Long?)
    suspend fun getLong(key: String): Long?

    suspend fun putDouble(key: String, value: Double?)
    suspend fun getDouble(key: String): Double?

    suspend fun remove(key: String)
    suspend fun clear()
}

class PreferencesImpl(
    context: Context,
    override val storageName: String
) : com.mk.androidshowcase.data.local.preferences.Preferences {

    private val dataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(storageName) }
        )

    override suspend fun putString(key: String, value: String?) =
        setOrRemoveIfNull(stringPreferencesKey(key), value)

    override suspend fun getString(key: String): String? =
        getValue(stringPreferencesKey(key))

    override suspend fun putInt(key: String, value: Int?) =
        setOrRemoveIfNull(intPreferencesKey(key), value)

    override suspend fun getInt(key: String): Int? =
        getValue(intPreferencesKey(key))

    override suspend fun putBoolean(key: String, value: Boolean?) =
        setOrRemoveIfNull(booleanPreferencesKey(key), value)

    override suspend fun getBoolean(key: String): Boolean? =
        getValue(booleanPreferencesKey(key))

    override suspend fun putFloat(key: String, value: Float?) =
        setOrRemoveIfNull(floatPreferencesKey(key), value)

    override suspend fun getFloat(key: String): Float? =
        getValue(floatPreferencesKey(key))

    override suspend fun putLong(key: String, value: Long?) =
        setOrRemoveIfNull(longPreferencesKey(key), value)

    override suspend fun getLong(key: String): Long? =
        getValue(longPreferencesKey(key))

    override suspend fun putDouble(key: String, value: Double?) =
        setOrRemoveIfNull(doublePreferencesKey(key), value)

    override suspend fun getDouble(key: String): Double? =
        getValue(doublePreferencesKey(key))

    override suspend fun remove(key: String) {
        dataStore.edit {
            it.remove(stringPreferencesKey(key))
            it.remove(intPreferencesKey(key))
            it.remove(booleanPreferencesKey(key))
            it.remove(floatPreferencesKey(key))
            it.remove(longPreferencesKey(key))
            it.remove(doublePreferencesKey(key))
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    private suspend fun <T> setOrRemoveIfNull(key: Preferences.Key<T>, value: T?) {
        dataStore.edit {
            if (value == null) {
                it.remove(key)
            } else
                it[key] = value
        }
    }

    private suspend fun <T> getValue(key: Preferences.Key<T>): T? {
        return dataStore.data.first()[key]
    }
}
