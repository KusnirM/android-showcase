package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    showClearButton: Boolean = true,
    onClear: (() -> Unit)? = null,
    supportingText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val clearButton: @Composable (() -> Unit)? = when {
        trailingIcon != null -> trailingIcon
        showClearButton && value.isNotEmpty() -> {
            {
                IconButton(
                    onClick = {
                        onValueChange("")
                        onClear?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.appColorScheme.neutral80
                    )
                }
            }
        }
        else -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { TextBodySmallNeutral80(it) } },
        placeholder = placeholder?.let { { TextBodyMediumNeutral80(it) } },
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
        leadingIcon = leadingIcon,
        trailingIcon = clearButton,
        supportingText = supportingText?.let { { TextBodySmallNeutral80(it) } },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.appColorScheme.neutral80,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.appColorScheme.neutral80,
            cursorColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.appColorScheme.error,
            errorLabelColor = MaterialTheme.appColorScheme.error
        )
    )
}
