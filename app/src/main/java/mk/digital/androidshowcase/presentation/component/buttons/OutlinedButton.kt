package mk.digital.androidshowcase.presentation.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mk.digital.androidshowcase.presentation.component.text.labelLarge.TextButtonPrimary
import mk.digital.androidshowcase.presentation.foundation.appColorScheme
import mk.digital.androidshowcase.presentation.foundation.cardCornerRadius6
import mk.digital.androidshowcase.presentation.foundation.space4

private val outlineButtonBorderSize: Dp = 1.dp


@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        border = BorderStroke(
            width = outlineButtonBorderSize,
            color = MaterialTheme.appColorScheme.primary,
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = LocalContentColor.current,
        ),
        contentPadding = PaddingValues(space4),
        shape = RoundedCornerShape(cardCornerRadius6),
        content = {
            TextButtonPrimary(text = text.uppercase())
        },
    )
}
