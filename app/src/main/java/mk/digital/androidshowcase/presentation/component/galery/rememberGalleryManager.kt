package mk.digital.androidshowcase.presentation.component.galery

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import mk.digital.androidshowcase.presentation.component.imagepicker.ImageResult
import mk.digital.androidshowcase.util.BitmapUtils

@Composable
fun rememberGalleryManager(onResult: (ImageResult?) -> Unit): GalleryManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val byteArray = BitmapUtils.getByteArray(uri, contentResolver)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            if (bitmap != null) {
                val result = ImageResult(byteArray, bitmap.asImageBitmap())
                onResult(result)
            } else {
                onResult(null)
            }
        } else {
            onResult(null)
        }
    }

    return remember {
        GalleryManager(onLaunch = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        })
    }
}

class GalleryManager(private val onLaunch: () -> Unit) {
    fun launch() {
        onLaunch()
    }
}
