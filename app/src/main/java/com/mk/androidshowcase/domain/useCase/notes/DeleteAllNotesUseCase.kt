package com.mk.androidshowcase.domain.useCase.notes

import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.useCase.base.None
import com.mk.androidshowcase.domain.useCase.base.UseCase
import javax.inject.Inject

class DeleteAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) : UseCase<None, Unit>() {
    override suspend fun run(params: None) = noteRepository.deleteAll()
}
