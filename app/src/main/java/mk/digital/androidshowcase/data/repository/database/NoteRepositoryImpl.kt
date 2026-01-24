package mk.digital.androidshowcase.data.repository.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import mk.digital.androidshowcase.data.database.AppDatabase
import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.model.NoteSortOption
import mk.digital.androidshowcase.domain.repository.NoteRepository

class NoteRepositoryImpl(
    database: AppDatabase
) : NoteRepository {

    private val queries = database.noteQueries

    override fun observeAll(sortOption: NoteSortOption): Flow<List<Note>> {
        val query = when (sortOption) {
            NoteSortOption.DATE_DESC -> queries.selectAll()
            NoteSortOption.DATE_ASC -> queries.selectAllByDateAsc()
            NoteSortOption.TITLE_ASC -> queries.selectAllByTitleAsc()
            NoteSortOption.TITLE_DESC -> queries.selectAllByTitleDesc()
        }
        return query.asFlow().mapToList(Dispatchers.IO).map { it.transformAll() }
    }

    override fun search(query: String, sortOption: NoteSortOption): Flow<List<Note>> {
        val dbQuery = when (sortOption) {
            NoteSortOption.DATE_DESC -> queries.search(query, query)
            NoteSortOption.DATE_ASC -> queries.searchByDateAsc(query, query)
            NoteSortOption.TITLE_ASC -> queries.searchByTitleAsc(query, query)
            NoteSortOption.TITLE_DESC -> queries.searchByTitleDesc(query, query)
        }
        return dbQuery.asFlow().mapToList(Dispatchers.IO).map { it.transformAll() }
    }

    override suspend fun getById(id: Long): Note? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()?.transform()
    }

    override suspend fun insert(note: Note): Unit = withContext(Dispatchers.IO) {
        queries.insert(note.title, note.content, note.createdAt)
    }

    override suspend fun update(note: Note): Unit = withContext(Dispatchers.IO) {
        queries.update(note.title, note.content, note.id)
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.IO) {
        queries.deleteById(id)
    }

    override suspend fun deleteAll(): Unit = withContext(Dispatchers.IO) {
        queries.deleteAll()
    }

    override suspend fun count(): Long = withContext(Dispatchers.IO) {
        queries.count().executeAsOne()
    }
}
