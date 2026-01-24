@file:Suppress("MaxLineLength")

package mk.digital.androidshowcase.presentation.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class IconPath(
    val fill: Color,
    val path: String,
)

object AppIcons {

    // Chevrons
    val ChevronUp: ImageVector by lazy {
        buildIcon(
            name = "ChevronUp",
            width = 24.dp,
            height = 24.dp,
            paths = listOf(
                IconPath(Color.Black, "M7.41,15.41L12,10.83l4.59,4.58L18,14l-6,-6l-6,6z")
            )
        )
    }

    val ChevronDown: ImageVector by lazy {
        buildIcon(
            name = "ChevronDown",
            width = 24.dp,
            height = 24.dp,
            paths = listOf(
                IconPath(Color.Black, "M7.41,8.59L12,13.17l4.59,-4.58L18,10l-6,6l-6,-6z")
            )
        )
    }

    // Flags
    val FlagSK: ImageVector by lazy {
        buildIcon(
            name = "FlagSK",
            width = 36.dp,
            height = 24.dp,
            viewportWidth = 300f,
            viewportHeight = 200f,
            paths = listOf(
                // Red background
                IconPath(Color(0xFFEE1C25), "M0,0h300v200H0z"),
                // Blue stripe (middle)
                IconPath(Color(0xFF0B4EA2), "M0,0h300v133.333H0z"),
                // White stripe (top)
                IconPath(Color(0xFFFFFFFF), "M0,0h300v66.667H0z"),
                // White shield outline
                IconPath(Color(0xFFFFFFFF), "M105.419,154.5c-17.927-8.635-43.57-25.774-43.57-59.678S63.469,45.5,63.469,45.5h41.949h41.95c0,0,1.621,15.419,1.621,49.322S123.347,145.865,105.419,154.5z"),
                // Red shield
                IconPath(Color(0xFFEE1C25), "M105.419,150c-16.447-7.922-39.973-23.646-39.973-54.751c0-31.104,1.487-45.25,1.487-45.25h38.485h38.486c0,0,1.488,14.146,1.488,45.25C145.392,126.354,121.866,142.078,105.419,150z"),
                // White cross
                IconPath(Color(0xFFFFFFFF), "M108.936,87.094c4.453,0.072,13.142,0.246,20.879-2.342c0,0-0.204,2.769-0.204,5.994c0,3.226,0.204,5.994,0.204,5.994c-7.096-2.375-15.86-2.424-20.879-2.361v17.18h-7.034v-17.18c-5.018-0.063-13.782-0.014-20.878,2.361c0,0,0.204-2.768,0.204-5.994c0-3.225-0.204-5.994-0.204-5.994c7.737,2.589,16.425,2.414,20.878,2.342v-10.789c-4.059-0.036-9.908,0.158-16.521,2.371c0,0,0.204-2.769,0.204-5.994c0-3.225-0.204-5.994-0.204-5.994c6.604,2.21,12.445,2.407,16.503,2.371c-0.208-6.832-2.199-15.443-2.199-15.443s4.096,0.32,5.734,0.32c1.639,0,5.734-0.32,5.734-0.32s-1.99,8.611-2.199,15.443c4.058,0.035,9.9-0.162,16.503-2.371c0,0-0.204,2.769-0.204,5.994c0,3.226,0.204,5.994,0.204,5.994c-6.613-2.213-12.462-2.407-16.521-2.371V87.094z"),
                // Blue hills
                IconPath(Color(0xFF0B4EA2), "M104.694,160.204l-34.078-30.377c0,0,4.357-14.091,12.86-14.091c6.757,0,9.224,5.449,9.224,5.449s4.437-11.486,12.719-11.486c8.283,0,12.72,11.486,12.72,11.486s2.465-5.449,9.223-5.449c8.504,0,12.86,14.091,12.86,14.091L104.694,160.204z"),
            )
        )
    }

    val FlagEN: ImageVector by lazy {
        buildIcon(
            name = "FlagEN",
            width = 36.dp,
            height = 36.dp,
            viewportWidth = 36f,
            viewportHeight = 36f,
            paths = listOf(
                // Blue background corners
                IconPath(Color(0xFF00247D), "M0 9.059V13h5.628zM4.664 31H13v-5.837zM23 25.164V31h8.335zM0 23v3.941L5.63 23zM31.337 5H23v5.837zM36 26.942V23h-5.631zM36 13V9.059L30.371 13zM13 5H4.664L13 10.837z"),
                // Red diagonals
                IconPath(Color(0xFFCF1B2B), "M25.14 23l9.712 6.801a3.977 3.977 0 0 0 .99-1.749L28.627 23H25.14zM13 23h-2.141l-9.711 6.8c.521.53 1.189.909 1.938 1.085L13 23.943V23zm10-10h2.141l9.711-6.8a3.988 3.988 0 0 0-1.937-1.085L23 12.057V13zm-12.141 0L1.148 6.2a3.994 3.994 0 0 0-.991 1.749L7.372 13h3.487z"),
                // White stripes
                IconPath(Color(0xFFEEEEEE), "M36 21H21v10h2v-5.836L31.335 31H32a3.99 3.99 0 0 0 2.852-1.199L25.14 23h3.487l7.215 5.052c.093-.337.158-.686.158-1.052v-.058L30.369 23H36v-2zM0 21v2h5.63L0 26.941V27c0 1.091.439 2.078 1.148 2.8l9.711-6.8H13v.943l-9.914 6.941c.294.07.598.116.914.116h.664L13 25.163V31h2V21H0zM36 9a3.983 3.983 0 0 0-1.148-2.8L25.141 13H23v-.943l9.915-6.942A4.001 4.001 0 0 0 32 5h-.663L23 10.837V5h-2v10h15v-2h-5.629L36 9.059V9zM13 5v5.837L4.664 5H4a3.985 3.985 0 0 0-2.852 1.2l9.711 6.8H7.372L.157 7.949A3.968 3.968 0 0 0 0 9v.059L5.628 13H0v2h15V5h-2z"),
                // Red cross
                IconPath(Color(0xFFCF1B2B), "M21 15V5h-6v10H0v6h15v10h6V21h15v-6z"),
            )
        )
    }

    // Multi-path icon builder (each path has its own fill color)
    private fun buildIcon(
        name: String,
        width: Dp,
        height: Dp,
        paths: List<IconPath>,
        viewportWidth: Float = width.value,
        viewportHeight: Float = height.value,
    ): ImageVector = ImageVector.Builder(
        name = name,
        defaultWidth = width,
        defaultHeight = height,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight
    ).apply {
        paths.forEach { iconPath ->
            addPath(
                fill = SolidColor(iconPath.fill),
                pathData = addPathNodes(iconPath.path)
            )
        }
    }.build()
}
