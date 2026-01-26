package com.mk.androidshowcase.presentation.component.biometric

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mk.androidshowcase.R
import com.mk.androidshowcase.presentation.component.image.AppIconPrimary
import com.mk.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80

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
                contentDescription = stringResource(R.string.login_biometric_hint_fingerprint),
                size = 48.dp,
            )
        }
        TextBodySmallNeutral80(stringResource(R.string.login_biometric_hint_fingerprint))
    }
}

