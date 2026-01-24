package mk.digital.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.Flow

@Composable
private fun ScreenLifecycleEffect(
    key: Any? = Unit,
    onCreate: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onDispose: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val currentOnCreate by rememberUpdatedState(onCreate)
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnDispose by rememberUpdatedState(onDispose)

    var isResumed by remember(key) { mutableStateOf(false) }
    var isFirstComposition by remember(key) { mutableStateOf(true) }

    LaunchedEffect(key) {
        if (isFirstComposition) {
            isFirstComposition = false
            currentOnCreate()
        }

        if (!isResumed && lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            isResumed = true
            currentOnResume()
        }
    }

    DisposableEffect(key) {
        onDispose {
            if (isResumed) {
                isResumed = false
                currentOnPause()
            }
            currentOnDispose()
        }
    }

    DisposableEffect(lifecycleOwner, key) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (!isResumed) {
                        isResumed = true
                        currentOnResume()
                    }
                }
                Lifecycle.Event.ON_PAUSE -> {
                    if (isResumed) {
                        isResumed = false
                        currentOnPause()
                    }
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ScreenLifecycleEffect(viewModel: ScreenLifecycle) {
    ScreenLifecycleEffect(
        key = viewModel,
        onCreate = viewModel::onCreated,
        onResume = viewModel::onResumed,
        onPause = viewModel::onPaused
    )
}

@Composable
fun CollectNavEvents(
    navEventFlow: Flow<NavEvent>,
    onEvent: (NavEvent) -> Unit
) {
    val currentOnEvent by rememberUpdatedState(onEvent)
    LaunchedEffect(navEventFlow) {
        navEventFlow.collect { event ->
            currentOnEvent(event)
        }
    }
}
