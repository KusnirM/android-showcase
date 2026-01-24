package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mk.digital.androidshowcase.presentation.component.image.AppIcon
import mk.digital.androidshowcase.presentation.component.image.AppImage
import mk.digital.androidshowcase.presentation.foundation.appColorScheme
import mk.digital.androidshowcase.presentation.foundation.space4

private val avatarSize = 120.dp
private val avatarShape = RoundedCornerShape(space4)

@Composable
fun AvatarView(
    state: AvatarState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(avatarSize)
            .aspectRatio(1f)
            .clip(avatarShape)
            .background(MaterialTheme.appColorScheme.neutral20),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is AvatarState.Loaded -> AppImage(
                bitmap = state.image,
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            AvatarState.Loading -> CircularProgress()

            AvatarState.Empty -> AppIcon(
                imageVector = Icons.Filled.Person,
                tint = MaterialTheme.appColorScheme.neutral80,
                size = avatarSize / 2
            )
        }
    }
}

sealed interface AvatarState {
    data object Empty : AvatarState
    data object Loading : AvatarState
    data class Loaded(val image: ImageBitmap) : AvatarState
}
