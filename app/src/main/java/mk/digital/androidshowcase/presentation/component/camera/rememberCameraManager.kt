package mk.digital.androidshowcase.presentation.component.camera

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider.getUriForFile
import mk.digital.androidshowcase.presentation.component.imagepicker.ImageResult
import mk.digital.androidshowcase.util.BitmapUtils
import java.io.File

@Composable
fun rememberCameraManager(onResult: (ImageResult?) -> Unit): CameraManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var launchRequested by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && imageUri != null) {
            val byteArray = BitmapUtils.getByteArray(imageUri!!, contentResolver)
            val bitmap = BitmapUtils.getBitmapFromUri(imageUri!!, byteArray, contentResolver)
                ?.asImageBitmap()
            onResult(bitmap?.let { ImageResult(byteArray, it) })
        } else {
            onResult(null)
        }
        launchRequested = false
        imageUri = null
    }

    LaunchedEffect(launchRequested, imageUri) {
        val uri = imageUri
        if (launchRequested && uri != null) {
            cameraLauncher.launch(uri)
        }
    }

    return remember {
        CameraManager {
            imageUri = createImageUri(context)
            launchRequested = true
        }
    }
}

class CameraManager(
    private val onLaunch: () -> Unit
) {
    fun launch() {
        onLaunch()
    }
}

private fun createImageUri(context: Context): Uri {
    val tempFile = File.createTempFile(
        "camera_${System.currentTimeMillis()}", ".jpg", context.cacheDir
    )
    val authority = context.applicationContext.packageName + ".provider"
    return getUriForFile(context, authority, tempFile)
}
