package com.mk.androidshowcase.domain.useCase.notes

import com.mk.androidshowcase.domain.model.Note
import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) : UseCase<Note, Unit>() {
    override suspend fun run(params: Note) = noteRepository.insert(params)
}
