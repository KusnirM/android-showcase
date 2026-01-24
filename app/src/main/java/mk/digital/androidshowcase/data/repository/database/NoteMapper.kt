package mk.digital.androidshowcase.data.repository.database

import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.data.database.Note as NoteEntity

fun NoteEntity.transform() = Note(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt
)

fun List<NoteEntity>.transformAll() = map(NoteEntity::transform)
