package com.mk.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

/**
 * A composable that observes lifecycle events (onResume, onPause).
 * Use this in screens that need to react to lifecycle changes.
 */
@Composable
fun LifecycleEffect(
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> currentOnResume()
                Lifecycle.Event.ON_PAUSE -> currentOnPause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

/**
 * A lifecycle-aware version of hiltViewModel that automatically connects
 * ViewModel's onResumed/onPaused to the composable's lifecycle.
 */
@Composable
inline fun <reified VM : BaseViewModel<*>> lifecycleAwareViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
): VM {
    val viewModel = hiltViewModel<VM>(viewModelStoreOwner = viewModelStoreOwner, key = key)
    LifecycleEffect(
        onResume = viewModel::onResume,
        onPause = viewModel::onPause
    )
    return viewModel
}
