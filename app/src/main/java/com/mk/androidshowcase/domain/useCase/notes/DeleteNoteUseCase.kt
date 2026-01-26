package com.mk.androidshowcase.domain.useCase.notes

import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) : UseCase<Long, Unit>() {
    override suspend fun run(params: Long) = noteRepository.delete(params)
}
