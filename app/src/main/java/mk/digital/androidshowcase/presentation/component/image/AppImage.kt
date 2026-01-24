package mk.digital.androidshowcase.presentation.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@Composable
fun AppImage(
    @DrawableRes resource: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null
) {
    AppImage(
        painter = painterResource(id = resource),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}

@Composable
fun AppImage(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null
) {
    AppImage(
        painter = rememberVectorPainter(image = imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}

@Composable
fun AppImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}

@Composable
fun AppImage(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null
) {
    AppImage(
        painter = BitmapPainter(bitmap),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}
