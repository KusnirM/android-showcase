package com.mk.androidshowcase.presentation.component.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        content = {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    )
}

@Composable
fun AppTextButtonError(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.appColorScheme.error,
        ),
        content = {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    )
}
