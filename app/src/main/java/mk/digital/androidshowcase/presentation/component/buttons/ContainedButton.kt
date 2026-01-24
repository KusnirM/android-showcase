package mk.digital.androidshowcase.presentation.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mk.digital.androidshowcase.presentation.component.text.labelLarge.TextButtonNeutral0
import mk.digital.androidshowcase.presentation.foundation.cardCornerRadius6
import mk.digital.androidshowcase.presentation.foundation.space4
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun ContainedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(space4),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(cardCornerRadius6)
    ) {
        TextButtonNeutral0(text = text)
    }
}

@Composable
fun ContainedButton(
    id: StringResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContainedButton(text = stringResource(resource = id), onClick = onClick, modifier = modifier)
}
