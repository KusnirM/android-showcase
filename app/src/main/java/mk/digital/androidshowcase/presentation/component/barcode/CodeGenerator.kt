package mk.digital.androidshowcase.presentation.component.barcode

import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import javax.inject.Inject

private const val CODE_SIZE = 512
private const val BARCODE_HEIGHT = 256
private const val CODE_MARGIN = 1

class CodeGenerator @Inject constructor() {
    fun generate(text: String, format: CodeFormat): ImageBitmap {
        val (width, height) = when (format) {
            CodeFormat.BARCODE -> CODE_SIZE to BARCODE_HEIGHT
            CodeFormat.QR_CODE -> CODE_SIZE to CODE_SIZE
        }
        val zxingFormat = when (format) {
            CodeFormat.BARCODE -> BarcodeFormat.CODE_128
            CodeFormat.QR_CODE -> BarcodeFormat.QR_CODE
        }
        val hints = mapOf(
            EncodeHintType.CHARACTER_SET to "UTF-8",
            EncodeHintType.MARGIN to CODE_MARGIN
        )
        val bitMatrix: BitMatrix = MultiFormatWriter()
            .encode(
                /* contents = */ text,
                /* format = */ zxingFormat,
                /* width = */ width,
                /* height = */ height,
                /* hints = */ hints
            )

        val pixels = IntArray(width * height) { i ->
            val x = i % width
            val y = i / width
            if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
        }

        val bitmap = createBitmap(width, height)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return bitmap.asImageBitmap()
    }
}

