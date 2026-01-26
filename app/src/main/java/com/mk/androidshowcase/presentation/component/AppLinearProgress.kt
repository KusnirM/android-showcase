package com.mk.androidshowcase.presentation.component

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppLinearProgress(
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.appColorScheme.neutral20
    )
}

@Composable
fun AppLinearProgress(
    progress: () -> Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.appColorScheme.neutral20
    )
}
