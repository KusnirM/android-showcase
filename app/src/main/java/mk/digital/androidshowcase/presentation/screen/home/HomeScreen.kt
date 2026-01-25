package mk.digital.androidshowcase.presentation.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharedFlow
import mk.digital.androidshowcase.presentation.base.CollectNavEvents
import mk.digital.androidshowcase.presentation.base.NavEvent
import mk.digital.androidshowcase.presentation.base.NavRouter
import mk.digital.androidshowcase.presentation.base.Route
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun HomeScreen(
    router: NavRouter<Route>,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state, viewModel::onFeatureClick)
    HomeNavEvents(router, viewModel.navEvent)
}

@Composable
fun HomeScreen(
    state: HomeUiState = HomeUiState(),
    onFeatureClick: (featureId: FeatureId) -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace,
        ),
        verticalArrangement = Arrangement.spacedBy(space4),
    ) {
        items(state.features, key = { it.id }) { feature ->
            FeatureCard(
                feature = feature,
                onClick = { onFeatureClick(feature.id) }
            )
        }
    }
}

@Composable
private fun HomeNavEvents(
    router: NavRouter<Route>,
    navEvent: SharedFlow<NavEvent>
) {
    CollectNavEvents(navEventFlow = navEvent) { event ->
        when (event) {
            is HomeNavEvent.ToFeature -> {
                when (event.featureId) {
                    FeatureId.UI_COMPONENTS -> router.navigateTo(Route.HomeSection.UiComponents)
                    FeatureId.NETWORKING -> router.navigateTo(Route.HomeSection.Networking)
                    FeatureId.STORAGE -> router.navigateTo(Route.HomeSection.Storage)
                    FeatureId.DATABASE -> router.navigateTo(Route.HomeSection.Database)
                    FeatureId.APIS -> router.navigateTo(Route.HomeSection.Apis)
                    FeatureId.SCANNER -> router.navigateTo(Route.HomeSection.Scanner)
                    FeatureId.CALENDAR -> router.navigateTo(Route.HomeSection.Calendar)
                    FeatureId.NOTIFICATIONS -> router.navigateTo(Route.HomeSection.Notifications)
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParams::class) state: HomeUiState
) {
    AppTheme {
        HomeScreen(state = state)
    }
}

internal class HomeScreenPreviewParams : PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(HomeUiState())
}
