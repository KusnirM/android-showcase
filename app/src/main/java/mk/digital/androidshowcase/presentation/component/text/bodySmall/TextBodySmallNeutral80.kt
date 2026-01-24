package mk.digital.androidshowcase.presentation.component.text.bodySmall

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun TextBodySmallNeutral80(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    TextBodySmall(
        text = text,
        color = MaterialTheme.appColorScheme.neutral80,
        modifier = modifier,
        textAlign = textAlign,
        fontWeight = FontWeight.Normal,
        lineHeight = lineHeight,
    )
}
