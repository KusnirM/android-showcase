package mk.digital.androidshowcase.data.repository.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mk.digital.androidshowcase.data.base.transformAll
import mk.digital.androidshowcase.data.database.dao.NoteDao
import mk.digital.androidshowcase.data.database.entity.NoteEntity
import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.model.NoteSortOption
import mk.digital.androidshowcase.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun observeAll(sortOption: NoteSortOption): Flow<List<Note>> {
        return when (sortOption) {
            NoteSortOption.DATE_DESC -> noteDao.observeAllByDateDesc()
            NoteSortOption.DATE_ASC -> noteDao.observeAllByDateAsc()
            NoteSortOption.TITLE_ASC -> noteDao.observeAllByTitleAsc()
            NoteSortOption.TITLE_DESC -> noteDao.observeAllByTitleDesc()
        }.map { it.transformAll() }
    }

    override fun search(query: String, sortOption: NoteSortOption): Flow<List<Note>> {
        return when (sortOption) {
            NoteSortOption.DATE_DESC -> noteDao.searchByDateDesc(query)
            NoteSortOption.DATE_ASC -> noteDao.searchByDateAsc(query)
            NoteSortOption.TITLE_ASC -> noteDao.searchByTitleAsc(query)
            NoteSortOption.TITLE_DESC -> noteDao.searchByTitleDesc(query)
        }.map { it.transformAll() }
    }

    override suspend fun getById(id: Long): Note? {
        return noteDao.getById(id)?.transform()
    }

    override suspend fun insert(note: Note) {
        noteDao.insert(NoteEntity(note))
    }

    override suspend fun update(note: Note) {
        noteDao.update(NoteEntity(note))
    }

    override suspend fun delete(id: Long) {
        noteDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    override suspend fun count(): Long {
        return noteDao.count()
    }
}
