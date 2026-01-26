package com.mk.androidshowcase.presentation.component.text.titleLarge

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun TextTitleLargePrimary(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    TextTitleLarge(
        text = text,
        color = MaterialTheme.appColorScheme.primary,
        modifier = modifier,
        textAlign = textAlign
    )
}
