package mk.digital.androidshowcase.presentation.component.galery

import mk.digital.androidshowcase.presentation.component.imagepicker.ImageResult
import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManager(onResult: (ImageResult?) -> Unit): GalleryManager

expect class GalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}
