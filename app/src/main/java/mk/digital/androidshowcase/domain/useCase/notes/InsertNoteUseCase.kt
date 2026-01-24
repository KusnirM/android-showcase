package mk.digital.androidshowcase.domain.useCase.notes

import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.repository.NoteRepository
import mk.digital.androidshowcase.domain.useCase.base.UseCase

class InsertNoteUseCase(
    private val noteRepository: NoteRepository
) : UseCase<Note, Unit>() {
    override suspend fun run(params: Note) = noteRepository.insert(params)
}
