package com.mk.androidshowcase.presentation.component.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mk.androidshowcase.presentation.foundation.cardElevation

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = MaterialTheme.shapes.medium
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = shape
    ) {
        Column(
            modifier = Modifier.clip(shape).then(modifier),
            content = content
        )
    }
}

@Composable
fun AppElevatedCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = MaterialTheme.shapes.medium
    val colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    val elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    val cardContent: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier.clip(shape).then(modifier),
            content = content
        )
    }

    // ElevatedCard has separate overloads for clickable/non-clickable - if-else is required
    if (onClick != null) {
        ElevatedCard(onClick = onClick, colors = colors, elevation = elevation, shape = shape, content = cardContent)
    } else {
        ElevatedCard(colors = colors, elevation = elevation, shape = shape, content = cardContent)
    }
}
