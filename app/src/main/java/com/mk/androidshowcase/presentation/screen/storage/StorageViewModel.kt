package com.mk.androidshowcase.presentation.screen.storage

import dagger.hilt.android.lifecycle.HiltViewModel
import com.mk.androidshowcase.domain.useCase.base.invoke
import com.mk.androidshowcase.domain.useCase.storage.ClearCacheUseCase
import com.mk.androidshowcase.domain.useCase.storage.LoadStorageDataUseCase
import com.mk.androidshowcase.domain.useCase.storage.ObserveStorageDataUseCase
import com.mk.androidshowcase.domain.useCase.storage.SetPersistentCounterUseCase
import com.mk.androidshowcase.domain.useCase.storage.SetSessionCounterUseCase
import com.mk.androidshowcase.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val loadStorageDataUseCase: LoadStorageDataUseCase,
    private val observeStorageDataUseCase: ObserveStorageDataUseCase,
    private val setSessionCounterUseCase: SetSessionCounterUseCase,
    private val setPersistentCounterUseCase: SetPersistentCounterUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
) : BaseViewModel<StorageUiState>(StorageUiState()) {

    override fun loadInitialData() {
        observe(
            onStart = { loadStorageDataUseCase() },
            flow = observeStorageDataUseCase(),
            onEach = { data ->
                newState {
                    it.copy(sessionCounter = data.sessionCounter, persistentCounter = data.persistentCounter)
                }
            }
        )
    }

    fun incrementSessionCounter() {
        val newValue = state.value.sessionCounter + 1
        execute(action = { setSessionCounterUseCase(newValue) })
    }

    fun decrementSessionCounter() {
        val newValue = (state.value.sessionCounter - 1).coerceAtLeast(0)
        execute(action = { setSessionCounterUseCase(newValue) })
    }

    fun incrementPersistentCounter() {
        val newValue = state.value.persistentCounter + 1
        execute(action = { setPersistentCounterUseCase(newValue) })
    }

    fun decrementPersistentCounter() {
        val newValue = (state.value.persistentCounter - 1).coerceAtLeast(0)
        execute(action = { setPersistentCounterUseCase(newValue) })
    }

    fun clearSession() {
        execute(action = { clearCacheUseCase() })
    }
}

data class StorageUiState(
    val sessionCounter: Int = 0,
    val persistentCounter: Int = 0
)
