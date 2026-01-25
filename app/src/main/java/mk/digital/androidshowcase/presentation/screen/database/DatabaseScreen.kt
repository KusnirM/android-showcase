package mk.digital.androidshowcase.presentation.screen.database

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.domain.model.Note
import mk.digital.androidshowcase.domain.model.NoteSortOption
import mk.digital.androidshowcase.presentation.base.lifecycleAwareViewModel
import mk.digital.androidshowcase.presentation.component.AppSearchField
import mk.digital.androidshowcase.presentation.component.AppTextField
import mk.digital.androidshowcase.presentation.component.ErrorView
import mk.digital.androidshowcase.presentation.component.LoadingView
import mk.digital.androidshowcase.presentation.component.buttons.ContainedButton
import mk.digital.androidshowcase.presentation.component.buttons.OutlinedButton
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.image.AppIconNeutral80
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import mk.digital.androidshowcase.presentation.component.text.labelMedium.TextLabelMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4
import kotlin.time.Instant

@Composable
fun DatabaseScreen(viewModel: DatabaseViewModel = lifecycleAwareViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DatabaseScreen(
        state = state,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSortOptionChanged = viewModel::onSortOptionChanged,
        onToggleFilterMenu = viewModel::toggleFilterMenu,
        onDismissFilterMenu = viewModel::dismissFilterMenu,
        onTitleChanged = viewModel::onTitleChanged,
        onContentChanged = viewModel::onContentChanged,
        onAddNote = viewModel::addNote,
        onDeleteNote = viewModel::deleteNote,
        onDeleteAllNotes = viewModel::deleteAllNotes
    )
}

@Composable
internal fun DatabaseScreen(
    state: DatabaseUiState,
    onSearchQueryChanged: (String) -> Unit = {},
    onSortOptionChanged: (NoteSortOption) -> Unit = {},
    onToggleFilterMenu: () -> Unit = {},
    onDismissFilterMenu: () -> Unit = {},
    onTitleChanged: (String) -> Unit = {},
    onContentChanged: (String) -> Unit = {},
    onAddNote: () -> Unit = {},
    onDeleteNote: (Long) -> Unit = {},
    onDeleteAllNotes: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace
        ),
        verticalArrangement = Arrangement.spacedBy(space4)
    ) {
        item {
            SearchBar(
                query = state.searchQuery,
                onQueryChanged = onSearchQueryChanged,
                sortOption = state.sortOption,
                onSortOptionChanged = onSortOptionChanged,
                showFilterMenu = state.showFilterMenu,
                onToggleFilterMenu = onToggleFilterMenu,
                onDismissFilterMenu = onDismissFilterMenu
            )
        }

        item {
            AddNoteCard(
                title = state.newNoteTitle,
                content = state.newNoteContent,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onAddClick = onAddNote
            )
        }

        if (state.error && state.notes.isEmpty()) {
            item {
                ErrorView(message = stringResource(R.string.database_error))
            }
        } else if (state.isLoading && state.notes.isEmpty()) {
            item {
                LoadingView()
            }
        } else if (state.notes.isEmpty()) {
            item {
                TextBodyMediumNeutral80(stringResource(R.string.database_empty))
            }
        }

        items(items = state.notes, key = { it.id }) { note ->
            NoteCard(
                note = note,
                onDeleteClick = { onDeleteNote(note.id) }
            )
        }

        if (state.notes.isNotEmpty()) {
            item {
                OutlinedButton(
                    text = stringResource(R.string.database_clear_all),
                    onClick = onDeleteAllNotes
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    sortOption: NoteSortOption,
    onSortOptionChanged: (NoteSortOption) -> Unit,
    showFilterMenu: Boolean,
    onToggleFilterMenu: () -> Unit,
    onDismissFilterMenu: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space4)
    ) {
        AppSearchField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            placeholder = stringResource(R.string.database_search_hint)
        )

        Box {
            IconButton(onClick = onToggleFilterMenu) {
                AppIconNeutral80(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = stringResource(R.string.database_filter)
                )
            }

            DropdownMenu(
                expanded = showFilterMenu,
                onDismissRequest = onDismissFilterMenu
            ) {
                TextLabelMediumNeutral80(
                    text = stringResource(R.string.database_sort_by),
                    modifier = Modifier.padding(horizontal = space4, vertical = space4)
                )
                DropdownMenuItem(
                    text = { TextBodyMediumNeutral80(stringResource(R.string.database_sort_date_newest)) },
                    onClick = { onSortOptionChanged(NoteSortOption.DATE_DESC) },
                    leadingIcon = if (sortOption == NoteSortOption.DATE_DESC) {
                        { TextBodyMediumNeutral80("✓") }
                    } else null
                )
                DropdownMenuItem(
                    text = { TextBodyMediumNeutral80(stringResource(R.string.database_sort_date_oldest)) },
                    onClick = { onSortOptionChanged(NoteSortOption.DATE_ASC) },
                    leadingIcon = if (sortOption == NoteSortOption.DATE_ASC) {
                        { TextBodyMediumNeutral80("✓") }
                    } else null
                )
                DropdownMenuItem(
                    text = { TextBodyMediumNeutral80(stringResource(R.string.database_sort_title_asc)) },
                    onClick = { onSortOptionChanged(NoteSortOption.TITLE_ASC) },
                    leadingIcon = if (sortOption == NoteSortOption.TITLE_ASC) {
                        { TextBodyMediumNeutral80("✓") }
                    } else null
                )
                DropdownMenuItem(
                    text = { TextBodyMediumNeutral80(stringResource(R.string.database_sort_title_desc)) },
                    onClick = { onSortOptionChanged(NoteSortOption.TITLE_DESC) },
                    leadingIcon = if (sortOption == NoteSortOption.TITLE_DESC) {
                        { TextBodyMediumNeutral80("✓") }
                    } else null
                )
            }
        }
    }
}

@Composable
private fun AddNoteCard(
    title: String,
    content: String,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onAddClick: () -> Unit,
) {
    AppElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4)
    ) {
        AppTextField(
            value = title,
            onValueChange = onTitleChanged,
            label = stringResource(R.string.database_title_label),
            placeholder = stringResource(R.string.database_title_hint),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer2()
        AppTextField(
            value = content,
            onValueChange = onContentChanged,
            label = stringResource(R.string.database_content_label),
            placeholder = stringResource(R.string.database_content_hint),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer2()
        ContainedButton(
            text = stringResource(R.string.database_add_note),
            onClick = onAddClick,
            enabled = title.isNotBlank()
        )
    }
}

@Composable
private fun NoteCard(
    note: Note,
    onDeleteClick: () -> Unit,
) {
    AppElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TextTitleLargeNeutral80(note.title)
                if (note.content.isNotBlank()) {
                    Spacer2()
                    TextBodyMediumNeutral80(note.content)
                }
                Spacer2()
                TextBodySmallNeutral80(formatTimestamp(note.createdAt))
            }
            IconButton(onClick = onDeleteClick) {
                AppIconNeutral80(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.database_delete)
                )
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date} ${localDateTime.hour.toString().padStart(2, '0')}:${
        localDateTime.minute.toString().padStart(2, '0')
    }"
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DatabaseScreenPreview(
    @PreviewParameter(DatabaseScreenPreviewParams::class) state: DatabaseUiState
) {
    AppTheme {
        DatabaseScreen(state = state)
    }
}

internal class DatabaseScreenPreviewParams : PreviewParameterProvider<DatabaseUiState> {
    override val values = sequenceOf(
        DatabaseUiState(isLoading = true),
        DatabaseUiState(error = true),
        DatabaseUiState(
            notes = listOf(
                Note(id = 1, title = "title", content = "content", createdAt = 0),
                Note(
                    id = 2, title = "title2", content = "content2", createdAt = 1769344378
                ),
            ),
            newNoteTitle = "New Note",
            newNoteContent = "Content",
            sortOption = NoteSortOption.DATE_ASC,
            showFilterMenu = true
        )
    )
}
