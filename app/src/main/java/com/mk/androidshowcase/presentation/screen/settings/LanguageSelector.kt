package com.mk.androidshowcase.presentation.screen.settings

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mk.androidshowcase.R
import com.mk.androidshowcase.presentation.component.AppAlertDialog
import com.mk.androidshowcase.presentation.component.AppRadioButton
import com.mk.androidshowcase.presentation.component.cards.AppElevatedCard
import com.mk.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral100
import com.mk.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargePrimary
import com.mk.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import com.mk.androidshowcase.presentation.foundation.space4

@Composable
fun LanguageSelector(
    currentLanguage: LanguageState,
    onLanguageSelected: (LanguageState) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    AppElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { showDialog = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(space4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space4)
        ) {
            Image(
                imageVector = currentLanguage.icon,
                contentDescription = stringResource(R.string.settings_language),
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                TextBodyLargePrimary(stringResource(R.string.settings_language))
                Spacer2()
                TextBodyMediumNeutral80(stringResource(currentLanguage.stringRes))
            }
        }
    }

    if (showDialog) {
        AppAlertDialog(
            title = stringResource(R.string.settings_language),
            onDismissRequest = { showDialog = false },
        ) {
            Column {
                LanguageState.entries.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = false
                                onLanguageSelected(language)
                            }
                            .padding(vertical = space4),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppRadioButton(
                            selected = currentLanguage == language,
                            onClick = {
                                showDialog = false
                                onLanguageSelected(language)
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
