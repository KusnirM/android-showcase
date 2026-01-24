package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.presentation.component.buttons.ContainedButton
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.foundation.space4

@Composable
fun ErrorView(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Column(
        modifier = modifier.padding(space4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextHeadlineMediumPrimary(stringResource(R.string.error_title))
        Spacer2()
        TextBodyMediumNeutral80(message)
        if (onRetry != null) {
            Spacer2()
            ContainedButton(
                text = stringResource(R.string.button_retry),
                onClick = onRetry
            )
        }
    }
}
