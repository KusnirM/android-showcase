package mk.digital.androidshowcase.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import mk.digital.androidshowcase.presentation.component.ext.noRippleClickable
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import mk.digital.androidshowcase.presentation.foundation.appColorScheme
import mk.digital.androidshowcase.presentation.foundation.space6
import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.content_description_back
import org.jetbrains.compose.resources.stringResource


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    backClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth().statusBarsPadding(),
        title = { title?.let { TextTitleLargeNeutral80(text = title) } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            navIcon?.let {
                Icon(
                    modifier = Modifier.size(space6).noRippleClickable(backClick),
                    imageVector = navIcon,
                    contentDescription = stringResource(Res.string.content_description_back),
                    tint = MaterialTheme.appColorScheme.neutral80,
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        },
    )
}
