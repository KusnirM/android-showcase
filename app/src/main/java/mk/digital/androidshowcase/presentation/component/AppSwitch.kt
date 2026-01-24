package mk.digital.androidshowcase.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mk.digital.androidshowcase.presentation.foundation.appColorScheme

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.appColorScheme.neutral0,
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            uncheckedThumbColor = MaterialTheme.appColorScheme.neutral0,
            uncheckedTrackColor = MaterialTheme.appColorScheme.neutral80,
            uncheckedBorderColor = MaterialTheme.appColorScheme.neutral80
        )
    )
}
