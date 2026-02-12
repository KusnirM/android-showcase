package com.mk.androidshowcase.presentation.component.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mk.androidshowcase.presentation.foundation.cardCornerRadius6
import com.mk.androidshowcase.presentation.foundation.space4


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
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(cardCornerRadius6)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun ContainedButton(
    @StringRes id: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContainedButton(text = stringResource(id = id), onClick = onClick, modifier = modifier)
}
