package mk.digital.androidshowcase.presentation.component.text.labelSmall

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
internal fun TextLabelSmall(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = { color },
        textAlign = textAlign
    )
}

