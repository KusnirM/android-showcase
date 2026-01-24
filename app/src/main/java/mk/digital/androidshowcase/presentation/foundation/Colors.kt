package mk.digital.androidshowcase.presentation.foundation

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

// Light theme colors
val Neutral0Light: Color = Color(0xFFFFFFFF)
internal val Neutral20Light: Color = Color(0xFFC8C8C8)
internal val Neutral40Light: Color = Color(0xFF919191)
internal val Neutral60Light: Color = Color(0xFF5A5A5A)
internal val Neutral80Light: Color = Color(0xFF232323)
internal val Neutral100Light: Color = Color.Black

internal val PrimaryLight: Color = Color(0xFF6200EE)
internal val PrimaryContainerLight: Color = Color(0xFF3700B3)
internal val SecondaryLight: Color = Color(0xFF03DAC6)
internal val SecondaryContainerLight: Color = Color(0xFF018786)
internal val BackgroundLight: Color = Neutral0Light
internal val SurfaceLight: Color = Neutral0Light
internal val ErrorLight: Color = Color(0xFFFF1A1A)
internal val ErrorContainerLight: Color = Color(0xFFF9DEDC)
internal val SuccessLight: Color = Color(0xFF4CAF50)
internal val WarningLight: Color = Color(0xFFFF9800)

// Dark theme colors
internal val Neutral0Dark: Color = Color(0xFF121212)
internal val Neutral20Dark: Color = Color(0xFF2C2C2C)
internal val Neutral40Dark: Color = Color(0xFF6E6E6E)
internal val Neutral60Dark: Color = Color(0xFFA0A0A0)
internal val Neutral80Dark: Color = Color(0xFFE0E0E0)
internal val Neutral100Dark: Color = Color.White

internal val PrimaryDark: Color = Color(0xFFBB86FC)
internal val PrimaryContainerDark: Color = Color(0xFF6200EE)
internal val SecondaryDark: Color = Color(0xFF03DAC6)
internal val SecondaryContainerDark: Color = Color(0xFF03DAC6)
internal val BackgroundDark: Color = Neutral0Dark
internal val SurfaceDark: Color = Color(0xFF1E1E1E)
internal val ErrorDark: Color = Color(0xFFCF6679)
internal val ErrorContainerDark: Color = Color(0xFF93000A)
internal val SuccessDark: Color = Color(0xFF81C784)
internal val WarningDark: Color = Color(0xFFFFB74D)

internal val Transparent: Color = Color(0x00)


data class AppColors(
    val material: ColorScheme,
    val neutral0: Color,
    val neutral20: Color,
    val neutral40: Color,
    val neutral60: Color,
    val neutral80: Color,
    val neutral100: Color,
    val success: Color,
    val warning: Color,
    val transparent: Color = Transparent,
) {
    val primary: Color get() = material.primary
    val error: Color get() = material.error
}
