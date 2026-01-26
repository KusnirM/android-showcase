package com.mk.androidshowcase.presentation.component

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.mk.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppCheckbox(
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.appColorScheme.primary,
        )
    )
}
