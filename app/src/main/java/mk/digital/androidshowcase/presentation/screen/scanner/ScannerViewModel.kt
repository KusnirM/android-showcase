package mk.digital.androidshowcase.presentation.screen.scanner

import androidx.compose.ui.graphics.ImageBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.component.barcode.CodeFormat
import mk.digital.androidshowcase.presentation.component.barcode.CodeGenerator
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val codeGenerator: CodeGenerator
) : BaseViewModel<ScannerUiState>(ScannerUiState()) {

    fun onModeChanged(index: Int) {
        newState { it.copy(selectedModeIndex = index, scannedResult = null) }
    }

    fun onFormatChanged(index: Int) {
        val format = if (index == 0) CodeFormat.QR_CODE else CodeFormat.BARCODE
        newState { it.copy(selectedFormatIndex = index, selectedFormat = format, generatedBitmap = null) }
    }

    fun onTextChanged(text: String) {
        newState { it.copy(inputText = text, generatedBitmap = null) }
    }

    fun generateCode() {
        val text = state.value.inputText
        if (text.isBlank()) return

        execute(
            action = { codeGenerator.generate(text, state.value.selectedFormat) },
            onSuccess = { bitmap -> newState { it.copy(generatedBitmap = bitmap) } }
        )
    }

    fun onCodeScanned(result: String) {
        newState { it.copy(scannedResult = result) }
    }

    fun clearScannedResult() {
        newState { it.copy(scannedResult = null) }
    }
}

data class ScannerUiState(
    val selectedModeIndex: Int = 0,
    val selectedFormatIndex: Int = 0,
    val selectedFormat: CodeFormat = CodeFormat.QR_CODE,
    val inputText: String = "",
    val generatedBitmap: ImageBitmap? = null,
    val scannedResult: String? = null
)
