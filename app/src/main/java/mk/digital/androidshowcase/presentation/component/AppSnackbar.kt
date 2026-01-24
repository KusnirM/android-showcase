package mk.digital.androidshowcase.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

enum class SnackbarType {
    Default,
    Success,
    Error,
    Warning
}

class AppSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val type: SnackbarType = SnackbarType.Default
) : SnackbarVisuals

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { snackbarData ->
            val type = (snackbarData.visuals as? AppSnackbarVisuals)?.type ?: SnackbarType.Default
            AppSnackbar(snackbarData, type)
        }
    )
}

@Composable
fun AppSnackbar(
    snackbarData: SnackbarData,
    type: SnackbarType = SnackbarType.Default,
    modifier: Modifier = Modifier
) {
    val containerColor = when (type) {
        SnackbarType.Default -> MaterialTheme.appColorScheme.neutral80
        SnackbarType.Success -> MaterialTheme.appColorScheme.success
        SnackbarType.Error -> MaterialTheme.appColorScheme.error
        SnackbarType.Warning -> MaterialTheme.appColorScheme.warning
    }

    val contentColor = when (type) {
        SnackbarType.Default -> MaterialTheme.appColorScheme.neutral0
        SnackbarType.Success -> MaterialTheme.appColorScheme.neutral0
        SnackbarType.Error -> MaterialTheme.appColorScheme.neutral0
        SnackbarType.Warning -> MaterialTheme.appColorScheme.neutral100
    }

    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = contentColor
    )
}

suspend fun SnackbarHostState.showSnackbar(
    message: String,
    type: SnackbarType = SnackbarType.Default,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short
) = showSnackbar(
    AppSnackbarVisuals(
        message = message,
        type = type,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration
    )
)
