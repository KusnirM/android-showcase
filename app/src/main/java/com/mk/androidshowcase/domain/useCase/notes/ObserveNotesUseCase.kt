package com.mk.androidshowcase.domain.useCase.notes

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.model.Note
import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.useCase.base.FlowUseCase
import com.mk.androidshowcase.domain.useCase.base.None
import javax.inject.Inject

class ObserveNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) : FlowUseCase<None, List<Note>>() {
    override fun run(params: None): Flow<List<Note>> = noteRepository.observeAll()
}
