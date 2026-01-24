package mk.digital.androidshowcase.data.repository.database

import mk.digital.androidshowcase.domain.model.Note

data class NoteEntity(
    public val id: Long,
    public val title: String,
    public val content: String,
    public val createdAt: Long,
)

fun NoteEntity.transform() = Note(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt
)

//fun List<NoteEntity>.transformAll() = map(NoteEntity::transform)
