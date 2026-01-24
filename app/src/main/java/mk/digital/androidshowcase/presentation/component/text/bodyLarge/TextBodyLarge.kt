package mk.digital.androidshowcase.presentation.component.text.bodyLarge

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun TextBodyLarge(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = { color },
        fontWeight = fontWeight,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun TextBodyLargeNeutral100(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign? = null,
) {
    TextBodyLarge(
        text = text,
        color = MaterialTheme.appColorScheme.neutral100,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Composable
fun TextBodyLargePrimary(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign? = null,
) {
    TextBodyLarge(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
