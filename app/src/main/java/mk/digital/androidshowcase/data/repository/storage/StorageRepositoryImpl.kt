package mk.digital.androidshowcase.data.repository.storage

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.data.local.StorageLocalStore
import mk.digital.androidshowcase.domain.model.StorageData
import mk.digital.androidshowcase.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageLocalStore: StorageLocalStore
) : StorageRepository {

    override val storageData: Flow<StorageData> = storageLocalStore.data

    override suspend fun loadInitialData() = storageLocalStore.load()

    override suspend fun setSessionCounter(value: Int) = storageLocalStore.setSessionCounter(value)

    override suspend fun setPersistentCounter(value: Int) = storageLocalStore.setPersistentCounter(value)

    override suspend fun clear() {
        storageLocalStore.clear()
    }
}
