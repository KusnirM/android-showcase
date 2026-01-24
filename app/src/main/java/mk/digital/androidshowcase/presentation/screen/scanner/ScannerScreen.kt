package mk.digital.androidshowcase.presentation.screen.scanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.presentation.component.AppTextField
import mk.digital.androidshowcase.presentation.component.barcode.CodeFormat
import mk.digital.androidshowcase.presentation.component.barcode.CodeScanner
import mk.digital.androidshowcase.presentation.component.buttons.AppSegmentedButton
import mk.digital.androidshowcase.presentation.component.buttons.ContainedButton
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.permission.PermissionType
import mk.digital.androidshowcase.presentation.component.permission.PermissionView
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer2
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer4
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer8
import mk.digital.androidshowcase.presentation.component.text.bodyLarge.TextBodyLargeNeutral80
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.headlineMedium.TextHeadlineMediumPrimary
import mk.digital.androidshowcase.presentation.foundation.floatingNavBarSpace
import mk.digital.androidshowcase.presentation.foundation.space4

@Suppress("CognitiveComplexMethod")
@Composable
fun ScannerScreen(viewModel: ScannerViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val modeOptions = listOf(
        stringResource(R.string.scanner_mode_generate),
        stringResource(R.string.scanner_mode_scan)
    )

    val formatOptions = listOf(
        stringResource(R.string.scanner_format_qr),
        stringResource(R.string.scanner_format_barcode)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(
            start = space4,
            end = space4,
            top = space4,
            bottom = floatingNavBarSpace
        ),
        verticalArrangement = Arrangement.spacedBy(space4)
    ) {
        item {
            Column {
                TextHeadlineMediumPrimary(stringResource(R.string.scanner_title))
                TextBodyMediumNeutral80(stringResource(R.string.scanner_subtitle))
            }
        }

        item {
            AppSegmentedButton(
                options = modeOptions,
                selectedIndex = state.selectedModeIndex,
                onSelectionChanged = viewModel::onModeChanged,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (state.selectedModeIndex == 0) {
            item {
                AppElevatedCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(space4)) {
                    AppSegmentedButton(
                        options = formatOptions,
                        selectedIndex = state.selectedFormatIndex,
                        onSelectionChanged = viewModel::onFormatChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer4()
                    AppTextField(
                        value = state.inputText,
                        onValueChange = viewModel::onTextChanged,
                        label = stringResource(R.string.scanner_input_label),
                        placeholder = stringResource(R.string.scanner_input_hint),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer4()
                    ContainedButton(
                        text = stringResource(R.string.scanner_generate_button),
                        onClick = viewModel::generateCode,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            state.generatedBitmap?.let { bitmap ->
                item {
                    AppElevatedCard(modifier = Modifier
                        .fillMaxWidth()
                        .padding(space4)) {
                        TextBodyMediumNeutral80(stringResource(R.string.scanner_result_title))
                        Spacer2()
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Generated Code",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (state.selectedFormat == CodeFormat.QR_CODE) 250.dp else 150.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer2()
                        TextBodyLargeNeutral80(
                            text = state.inputText,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer8()
                    }
                }
            }
        } else {
            item {
                AppElevatedCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(space4)) {
                    TextBodyMediumNeutral80(stringResource(R.string.scanner_hint))
                    Spacer4()

                    if (state.scannedResult == null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            PermissionView(
                                PermissionType.CAMERA,
                                onDeniedDialogDismiss = {},
                            ) {
                                CodeScanner(
                                    onScanned = viewModel::onCodeScanned,
                                    onError = { },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextBodyMediumNeutral80(stringResource(R.string.scanner_scanned_result))
                            Spacer2()
                            TextBodyLargeNeutral80(state.scannedResult ?: "")
                            Spacer4()
                            ContainedButton(
                                text = stringResource(R.string.scanner_scan_again),
                                onClick = viewModel::clearScannedResult,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
