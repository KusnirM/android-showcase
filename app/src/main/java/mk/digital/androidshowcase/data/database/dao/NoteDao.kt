package mk.digital.androidshowcase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.data.database.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun observeAllByDateDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY createdAt ASC")
    fun observeAllByDateAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun observeAllByTitleAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY title DESC")
    fun observeAllByTitleDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchByDateDesc(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY createdAt ASC")
    fun searchByDateAsc(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchByTitleAsc(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY title DESC")
    fun searchByTitleDesc(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getById(id: Long): NoteEntity?

    @Insert
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM notes")
    suspend fun count(): Long
}
