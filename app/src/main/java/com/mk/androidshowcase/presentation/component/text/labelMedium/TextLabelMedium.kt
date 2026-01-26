package com.mk.androidshowcase.presentation.component.text.labelMedium

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
internal fun TextLabelMedium(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        fontWeight = FontWeight.Medium,
        textAlign = textAlign
    )
}

@Composable
fun TextLabelMediumNeutral80(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
) {
    TextLabelMedium(
        modifier = modifier,
        text = text,
        color = MaterialTheme.appColorScheme.neutral80,
        textAlign = textAlign
    )
}
