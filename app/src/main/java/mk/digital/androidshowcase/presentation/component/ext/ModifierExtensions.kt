package mk.digital.androidshowcase.presentation.component.ext

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 *  Checks if the composable is being rendered from a test or not
 */
val LocalTestMode = staticCompositionLocalOf { false }

@Composable
fun Modifier.verticalSafeScroll(state: ScrollState = rememberScrollState()) =
    then(if (!LocalTestMode.current) Modifier.verticalScroll(state) else Modifier)

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}
