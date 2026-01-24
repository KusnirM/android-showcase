package mk.digital.androidshowcase.presentation.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageSelector(
    currentLanguage: LanguageState,
    onNavigate: (SettingNavEvents) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    AppElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { showDialog = true }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(space4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space4)
        ) {
            Image(
                imageVector = currentLanguage.icon,
                contentDescription = stringResource(Res.string.settings_language),
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                TextBodyLargePrimary(stringResource(Res.string.settings_language))
                Spacer2()
                TextBodyMediumNeutral80(stringResource(currentLanguage.stringRes))
            }
        }
    }

    if (showDialog) {
        AppAlertDialog(
            title = stringResource(Res.string.settings_language),
            onDismissRequest = { showDialog = false },
        ) {
            Column {
                LanguageState.entries.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                onNavigate(SettingNavEvents.SetLocaleTag(language.tag))
                            }
                            .padding(vertical = space4),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppRadioButton(
                            selected = currentLanguage == language,
                            onClick = {
                                showDialog = false
                                onNavigate(SettingNavEvents.SetLocaleTag(language.tag))
                            }
                        )
                        Image(
                            imageVector = language.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        TextBodyLargeNeutral100(
                            text = stringResource(language.stringRes),
                            modifier = Modifier.padding(start = space4)
                        )
                    }
                }
            }
        }
    }
}

