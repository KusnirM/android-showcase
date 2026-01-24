package mk.digital.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified VM : BaseViewModel<*>> WithViewModel(
    content: @Composable (VM) -> Unit
) {
    val viewModel = koinViewModel<VM>()
    (viewModel as? ScreenLifecycle)?.let { ScreenLifecycleEffect(it) }
    content(viewModel)
}

@Composable
inline fun <reified VM : BaseViewModel<*>> WithViewModel(
    noinline parameters: ParametersDefinition,
    content: @Composable (VM) -> Unit
) {
    val viewModel = koinViewModel<VM>(parameters = parameters)
    (viewModel as? ScreenLifecycle)?.let { ScreenLifecycleEffect(it) }
    content(viewModel)
}
