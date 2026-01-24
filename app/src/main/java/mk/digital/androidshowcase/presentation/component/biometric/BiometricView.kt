package mk.digital.androidshowcase.presentation.component.biometric

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mk.digital.androidshowcase.presentation.component.image.AppIconPrimary

@Composable
fun BiometricView(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(64.dp)
        ) {
            AppIconPrimary(
                imageVector = Icons.Filled.Fingerprint,
                contentDescription = stringResource(Res.string.login_biometric_hint_fingerprint),
                size = 48.dp,
            )
        }
        TextBodySmallNeutral80(stringResource(Res.string.login_biometric_hint_fingerprint))
    }
}

