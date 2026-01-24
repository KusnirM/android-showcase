package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import mk.digital.androidshowcase.presentation.foundation.space8

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    size: Dp = space8,
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth
    )
}
