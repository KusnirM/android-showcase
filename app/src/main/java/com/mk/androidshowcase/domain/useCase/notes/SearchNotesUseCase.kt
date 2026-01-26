package com.mk.androidshowcase.domain.useCase.notes

import kotlinx.coroutines.flow.Flow
import com.mk.androidshowcase.domain.model.Note
import com.mk.androidshowcase.domain.model.NoteSortOption
import com.mk.androidshowcase.domain.repository.NoteRepository
import com.mk.androidshowcase.domain.useCase.base.FlowUseCase
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) : FlowUseCase<SearchNotesUseCase.Params, List<Note>>() {

    override fun run(params: Params): Flow<List<Note>> {
        return if (params.query.isBlank()) {
            repository.observeAll(params.sortOption)
        } else {
            repository.search(params.query, params.sortOption)
        }
    }

    data class Params(
        val query: String = "",
        val sortOption: NoteSortOption = NoteSortOption.DATE_DESC,
    )
}
