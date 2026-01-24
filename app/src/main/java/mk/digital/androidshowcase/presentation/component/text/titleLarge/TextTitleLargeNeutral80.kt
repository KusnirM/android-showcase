package mk.digital.androidshowcase.presentation.component.text.titleLarge

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun TextTitleLargeNeutral80(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    TextTitleLarge(
        text = text,
        modifier = modifier,
        color = MaterialTheme.appColorScheme.neutral80,
        textAlign = textAlign
    )
}
