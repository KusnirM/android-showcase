package mk.digital.androidshowcase.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface

object BitmapUtils {

    private const val TAG = "BitmapUtils"

    private const val ROTATION_90 = 90f
    private const val ROTATION_180 = 180f
    private const val ROTATION_270 = 270f

    fun getByteArray(uri: Uri, contentResolver: ContentResolver): ByteArray {
        return contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: ByteArray(0)
    }

    @Suppress("TooGenericExceptionCaught")
    fun getBitmapFromUri(
        uri: Uri,
        byteArray: ByteArray,
        contentResolver: ContentResolver
    ): Bitmap? {
        return try {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                ?: return null

            val orientation = contentResolver.openInputStream(uri)?.use { inputStream ->
                ExifInterface(inputStream).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            } ?: ExifInterface.ORIENTATION_NORMAL

            rotateBitmap(bitmap, orientation)
        } catch (e: Exception) {
            Log.e(TAG, "getBitmapFromUri failed: ${e.message}", e)
            null
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        if (orientation == ExifInterface.ORIENTATION_NORMAL) {
            return bitmap
        }

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(ROTATION_90)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(ROTATION_180)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(ROTATION_270)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.postRotate(ROTATION_90)
                matrix.preScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.postRotate(ROTATION_270)
                matrix.preScale(-1f, 1f)
            }

            else -> return bitmap
        }

        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )

        if (rotatedBitmap != bitmap) {
            bitmap.recycle()
        }

        return rotatedBitmap
    }
}