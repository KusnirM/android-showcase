package mk.digital.androidshowcase.presentation.component.imagepicker

import androidx.compose.ui.graphics.ImageBitmap

data class ImageResult(
    val byteArray: ByteArray,
    val bitmap: ImageBitmap
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ImageResult

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (bitmap != other.bitmap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + bitmap.hashCode()
        return result
    }
}
