package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodySmall.TextBodySmallNeutral80
import mk.digital.androidshowcase.presentation.foundation.appColorScheme
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.password_hide
import mk.digital.androidshowcase.shared.generated.resources.password_show
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { TextBodySmallNeutral80(it) } },
        placeholder = placeholder?.let { { TextBodyMediumNeutral80(it) } },
        enabled = enabled,
        isError = isError,
        singleLine = true,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = keyboardActions,
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = stringResource(
                        if (passwordVisible) Res.string.password_hide else Res.string.password_show
                    ),
                    tint = MaterialTheme.appColorScheme.neutral80
                )
            }
        },
        supportingText = supportingText?.let { { TextBodySmallNeutral80(it) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.appColorScheme.neutral80,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.appColorScheme.neutral80,
            focusedPlaceholderColor = MaterialTheme.appColorScheme.neutral60,
            unfocusedPlaceholderColor = MaterialTheme.appColorScheme.neutral60,
            cursorColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.appColorScheme.error,
            errorLabelColor = MaterialTheme.appColorScheme.error
        )
    )
}
