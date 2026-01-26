package com.mk.androidshowcase.presentation.screen.storage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.androidshowcase.R
import com.mk.androidshowcase.presentation.component.buttons.OutlinedButton
import com.mk.androidshowcase.presentation.component.cards.AppElevatedCard
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import com.mk.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import com.mk.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import com.mk.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import com.mk.androidshowcase.presentation.foundation.AppTheme
import com.mk.androidshowcase.presentation.foundation.floatingNavBarSpace
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun StorageScreen(viewModel: StorageViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StorageScreen(
        state = state,
        onSessionIncrement = viewModel::incrementSessionCounter,
        onSessionDecrement = viewModel::decrementSessionCounter,
        onPersistentIncrement = viewModel::incrementPersistentCounter,
        onPersistentDecrement = viewModel::decrementPersistentCounter,
        onClearSession = viewModel::clearSession
    )
}

@Composable
internal fun StorageScreen(
    state: StorageUiState,
    onSessionIncrement: () -> Unit = {},
    onSessionDecrement: () -> Unit = {},
    onPersistentIncrement: () -> Unit = {},
    onPersistentDecrement: () -> Unit = {},
    onClearSession: () -> Unit = {}
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
            Column {
                TextHeadlineMediumPrimary(stringResource(R.string.storage_title))
                TextBodyMediumNeutral80(stringResource(R.string.storage_subtitle))
            }
        }

        item {
            CounterCard(
                label = stringResource(R.string.storage_session_label),
                hint = stringResource(R.string.storage_session_hint),
                counter = state.sessionCounter,
                onIncrement = onSessionIncrement,
                onDecrement = onSessionDecrement
            )
        }

        item {
            CounterCard(
                label = stringResource(R.string.storage_persistent_label),
                hint = stringResource(R.string.storage_persistent_hint),
                counter = state.persistentCounter,
                onIncrement = onPersistentIncrement,
                onDecrement = onPersistentDecrement
            )
        }

        item {
            OutlinedButton(
                text = stringResource(R.string.storage_clear_session),
                onClick = onClearSession
            )
        }
    }
}

@Composable
private fun CounterCard(
    label: String,
    hint: String,
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    AppElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(space4)) {
        TextBodyLargeNeutral80(label)
        Spacer2()
        TextBodyMediumNeutral80(hint)
        Spacer2()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space4)
        ) {
            IconButton(onClick = onDecrement) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Decrease",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            TextTitleLargeNeutral80(counter.toString())
            IconButton(onClick = onIncrement) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increase",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StorageScreenPreview(
    @PreviewParameter(StorageScreenPreviewParams::class) state: StorageUiState
) {
    AppTheme {
        StorageScreen(state = state)
    }
}

internal class StorageScreenPreviewParams : PreviewParameterProvider<StorageUiState> {
    override val values = sequenceOf(
        StorageUiState(),
        StorageUiState(sessionCounter = 5, persistentCounter = 10)
    )
}
