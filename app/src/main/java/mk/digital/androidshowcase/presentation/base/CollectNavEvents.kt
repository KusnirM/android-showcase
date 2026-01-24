package mk.digital.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : NavEvent> CollectNavEvents(
    navEventFlow: Flow<T>,
    onEvent: (T) -> Unit
) {
    val currentOnEvent by rememberUpdatedState(onEvent)
    LaunchedEffect(navEventFlow) {
        navEventFlow.collect { event ->
            currentOnEvent(event)
        }
    }
}
