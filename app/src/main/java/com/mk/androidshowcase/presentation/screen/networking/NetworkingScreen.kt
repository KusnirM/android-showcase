package com.mk.androidshowcase.presentation.screen.networking

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
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
import com.mk.androidshowcase.domain.model.Address
import com.mk.androidshowcase.domain.model.User
import com.mk.androidshowcase.presentation.component.CircularProgress
import com.mk.androidshowcase.presentation.component.ErrorView
import com.mk.androidshowcase.presentation.component.LoadingView
import com.mk.androidshowcase.presentation.component.cards.AppElevatedCard
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import com.mk.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import com.mk.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import com.mk.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import com.mk.androidshowcase.presentation.foundation.AppTheme
import com.mk.androidshowcase.presentation.foundation.floatingNavBarSpace
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun NetworkingScreen(viewModel: NetworkingViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NetworkingScreen(state = state, onRefresh = viewModel::refresh)
}

@Composable
internal fun NetworkingScreen(
    state: NetworkingUiState,
    onRefresh: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading && state.users.isEmpty() -> LoadingView()
            state.error != null && state.users.isEmpty() -> ErrorView(
                message = state.error,
                onRetry = onRefresh
            )

            state.users.isEmpty() -> EmptyContent()
            else -> UserListContent(
                users = state.users,
                isRefreshing = state.isLoading,
                onRefresh = onRefresh
            )
        }
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextBodyMediumNeutral80(stringResource(R.string.networking_empty))
    }
}

@Composable
private fun UserListContent(
    users: List<User>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header with refresh button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = space4, vertical = space4),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                TextHeadlineMediumPrimary(stringResource(R.string.networking_title))
                TextBodyMediumNeutral80(stringResource(R.string.networking_subtitle))
            }
            IconButton(onClick = onRefresh, enabled = !isRefreshing) {
                if (isRefreshing) {
                    CircularProgress()
                } else {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // User list
        LazyColumn(
            contentPadding = PaddingValues(
                start = space4,
                end = space4,
                bottom = floatingNavBarSpace
            ),
            verticalArrangement = Arrangement.spacedBy(space4)
        ) {
            items(users, key = { it.id }) { user ->
                UserCard(user = user)
            }
        }
    }
}

@Composable
private fun UserCard(user: User) {
    AppElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(space4)
    ) {
        TextTitleLargeNeutral80(user.name)
        Spacer2()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            TextBodyMediumNeutral80(
                text = user.email,
                modifier = Modifier.padding(start = space4)
            )
        }
        Spacer2()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            TextBodyMediumNeutral80(
                text = "${user.address.city}, ${user.address.street}",
                modifier = Modifier.padding(start = space4)
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NetworkingScreenPreview(
    @PreviewParameter(NetworkingScreenPreviewParams::class) state: NetworkingUiState
) {
    AppTheme {
        NetworkingScreen(state = state)
    }
}

internal class NetworkingScreenPreviewParams : PreviewParameterProvider<NetworkingUiState> {
    override val values = sequenceOf(
        NetworkingUiState(isLoading = true),
        NetworkingUiState(error = "401"),
        NetworkingUiState(users = listOf(
            User(
                address = Address(
                    city = "city",
                    street = "street",
                    suite = "suite",
                    zipcode = "zipcode"
                ),
                email = "mir.kusnir@gmail.com",
                id = 1,
                name = "Miroslav Coder"
            )
        ))
    )
}
