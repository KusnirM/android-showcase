package com.mk.androidshowcase.presentation.component

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.appColorScheme.neutral0,
            containerColor = MaterialTheme.appColorScheme.neutral0,
            labelColor = MaterialTheme.appColorScheme.neutral100
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = selected,
            borderColor = MaterialTheme.appColorScheme.neutral80,
            selectedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun AppAssistChip(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    AssistChip(
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.appColorScheme.neutral0,
            labelColor = MaterialTheme.appColorScheme.neutral100,
            leadingIconContentColor = MaterialTheme.colorScheme.primary,
            trailingIconContentColor = MaterialTheme.appColorScheme.neutral80
        ),
        border = AssistChipDefaults.assistChipBorder(
            enabled = enabled,
            borderColor = MaterialTheme.appColorScheme.neutral80
        )
    )
}

@Composable
fun AppInputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.appColorScheme.neutral0,
            containerColor = MaterialTheme.appColorScheme.neutral0,
            labelColor = MaterialTheme.appColorScheme.neutral100
        ),
        border = InputChipDefaults.inputChipBorder(
            enabled = enabled,
            selected = selected,
            borderColor = MaterialTheme.appColorScheme.neutral80,
            selectedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun AppSuggestionChip(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null
) {
    SuggestionChip(
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.appColorScheme.neutral0,
            labelColor = MaterialTheme.appColorScheme.neutral100,
            iconContentColor = MaterialTheme.colorScheme.primary
        ),
        border = SuggestionChipDefaults.suggestionChipBorder(
            enabled = enabled,
            borderColor = MaterialTheme.appColorScheme.neutral80
        )
    )
}
