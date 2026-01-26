package com.mk.androidshowcase.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.mk.androidshowcase.data.local.preferences.PersistentPreferences
import com.mk.androidshowcase.data.local.preferences.SessionPreferences
import com.mk.androidshowcase.domain.model.StorageData
import javax.inject.Inject

interface StorageLocalStore {
    val data: Flow<StorageData>

    suspend fun load()
    suspend fun setSessionCounter(value: Int)
    suspend fun setPersistentCounter(value: Int)
    suspend fun clear()
}

class StorageLocalStoreImpl @Inject constructor(
    private val sessionPreferences: SessionPreferences,
    private val persistentPreferences: PersistentPreferences
) : StorageLocalStore {

    private val _data = MutableStateFlow(StorageData())
    override val data: Flow<StorageData> = _data.asStateFlow()

    override suspend fun load() {
        _data.value = StorageData(
            sessionCounter = sessionPreferences.getSessionCounter(),
            persistentCounter = persistentPreferences.getPersistentCounter()
        )
    }

    override suspend fun setSessionCounter(value: Int) {
        sessionPreferences.setSessionCounter(value)
        _data.update { it.copy(sessionCounter = value) }
    }

    override suspend fun setPersistentCounter(value: Int) {
        persistentPreferences.setPersistentCounter(value)
        _data.update { it.copy(persistentCounter = value) }
    }

    override suspend fun clear() {
        sessionPreferences.clear()
        _data.update { it.copy(sessionCounter = 0) }
    }
}
