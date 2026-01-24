package mk.digital.androidshowcase.presentation.screen.database

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.model.NoteSortOption
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.notes.DeleteAllNotesUseCase
import mk.digital.androidshowcase.domain.useCase.notes.DeleteNoteUseCase
import mk.digital.androidshowcase.domain.useCase.notes.InsertNoteUseCase
import mk.digital.androidshowcase.domain.useCase.notes.SearchNotesUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import javax.inject.Inject
import kotlin.time.Clock

private const val SEARCH_DEBOUNCE_MS = 300L

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val searchNotesUseCase: SearchNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase,
) : BaseViewModel<DatabaseUiState>(DatabaseUiState()) {

    private val searchTrigger = MutableStateFlow(SearchTrigger())
    private var searchJob: Job? = null
    private var debounceJob: Job? = null

    @OptIn(FlowPreview::class)
    fun onResumed() {
        debounceJob = searchTrigger
            .debounce(SEARCH_DEBOUNCE_MS)
            .onEach { trigger -> executeSearch(trigger.query, trigger.sortOption) }
            .launchIn(viewModelScope)

        triggerSearch()
    }

    fun onPaused() {
        debounceJob?.cancel()
        debounceJob = null
        searchJob?.cancel()
        searchJob = null
    }

    private fun executeSearch(query: String, sortOption: NoteSortOption) {
        searchJob?.cancel()
        searchJob = observe(
            flow = searchNotesUseCase(SearchNotesUseCase.Params(query, sortOption)),
            onEach = { notes -> newState { it.copy(notes = notes, isLoading = false) } },
            onError = { newState { it.copy(isLoading = false, error = true) } }
        )
    }

    private fun triggerSearch() {
        val currentState = state.value
        searchTrigger.value = SearchTrigger(currentState.searchQuery, currentState.sortOption)
    }

    fun onSearchQueryChanged(query: String) {
        newState { it.copy(searchQuery = query) }
        triggerSearch()
    }

    fun onSortOptionChanged(sortOption: NoteSortOption) {
        newState { it.copy(sortOption = sortOption, showFilterMenu = false) }
        triggerSearch()
    }

    fun toggleFilterMenu() {
        newState { it.copy(showFilterMenu = !it.showFilterMenu) }
    }

    fun dismissFilterMenu() {
        newState { it.copy(showFilterMenu = false) }
    }

    fun onTitleChanged(title: String) {
        newState { it.copy(newNoteTitle = title) }
    }

    fun onContentChanged(content: String) {
        newState { it.copy(newNoteContent = content) }
    }

    fun addNote() {
        val currentState = state.value
        if (currentState.newNoteTitle.isBlank()) return

        val note = Note(
            title = currentState.newNoteTitle.trim(),
            content = currentState.newNoteContent.trim(),
            createdAt = Clock.System.now().toEpochMilliseconds()
        )

        execute(
            action = { insertNoteUseCase(note) },
            onSuccess = { newState { it.copy(newNoteTitle = "", newNoteContent = "") } }
        )
    }

    fun deleteNote(id: Long) {
        execute(action = { deleteNoteUseCase(id) })
    }

    fun deleteAllNotes() {
        execute(action = { deleteAllNotesUseCase() })
    }

    private data class SearchTrigger(
        val query: String = "",
        val sortOption: NoteSortOption = NoteSortOption.DATE_DESC,
    )
}

data class DatabaseUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val error: Boolean = false,
    val newNoteTitle: String = "",
    val newNoteContent: String = "",
    val searchQuery: String = "",
    val sortOption: NoteSortOption = NoteSortOption.DATE_DESC,
    val showFilterMenu: Boolean = false,
)
