package mk.digital.androidshowcase.domain.useCase.notes

import kotlinx.coroutines.flow.Flow
import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.repository.NoteRepository
import mk.digital.androidshowcase.domain.useCase.base.FlowUseCase
import mk.digital.androidshowcase.domain.useCase.base.None

class ObserveNotesUseCase(
    private val noteRepository: NoteRepository
) : FlowUseCase<None, List<Note>>() {
    override fun run(params: None): Flow<List<Note>> = noteRepository.observeAll()
}
