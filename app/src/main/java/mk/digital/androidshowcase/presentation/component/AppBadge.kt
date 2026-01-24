package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

private const val DEFAULT_MAX_COUNT = 99

@Composable
fun AppBadge(
    modifier: Modifier = Modifier,
    count: Int? = null,
    maxCount: Int = DEFAULT_MAX_COUNT,
) {
    Badge(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.appColorScheme.neutral0
    ) {
        count?.let {
            Text(text = if (it > maxCount) "$maxCount+" else it.toString())
        }
    }
}

@Composable
fun AppBadgedBox(
    badge: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    BadgedBox(
        badge = badge,
        modifier = modifier,
        content = content
    )
}

@Composable
fun AppBadgedBox(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = DEFAULT_MAX_COUNT,
    showBadge: Boolean = count > 0,
    content: @Composable BoxScope.() -> Unit,
) {
    BadgedBox(
        badge = { if (showBadge) AppBadge(count = count, maxCount = maxCount) },
        modifier = modifier,
        content = content
    )
}

@Composable
fun AppDotBadge(
    modifier: Modifier = Modifier,
) {
    Badge(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun AppDotBadgedBox(
    showBadge: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    BadgedBox(
        badge = { if (showBadge) AppDotBadge() },
        modifier = modifier,
        content = content
    )
}
