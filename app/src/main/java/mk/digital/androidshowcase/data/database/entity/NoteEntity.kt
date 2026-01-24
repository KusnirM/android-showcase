package mk.digital.androidshowcase.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import mk.digital.androidshowcase.data.base.TransformToDomainModel
import mk.digital.androidshowcase.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long,
) : TransformToDomainModel<Note> {
    override fun transform(): Note = Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt
    )

    constructor(note: Note) : this(
        id = note.id,
        title = note.title,
        content = note.content,
        createdAt = note.createdAt
    )
}
