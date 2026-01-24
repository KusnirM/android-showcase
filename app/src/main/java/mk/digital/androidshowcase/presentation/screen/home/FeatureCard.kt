package mk.digital.androidshowcase.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mk.digital.androidshowcase.presentation.component.cards.AppElevatedCard
import mk.digital.androidshowcase.presentation.component.image.AppIconPrimary
import mk.digital.androidshowcase.presentation.component.spacers.ColumnSpacer.Spacer4
import mk.digital.androidshowcase.presentation.component.text.bodyMedium.TextBodyMediumNeutral80
import mk.digital.androidshowcase.presentation.component.text.titleLarge.TextTitleLargeNeutral80
import mk.digital.androidshowcase.presentation.foundation.space10
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.foundation.space8


private val featureCardIconSize = space10

@Composable
fun FeatureCard(
    feature: Feature,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = stringResource(feature.titleRes)
    val subtitle = stringResource(feature.subtitleRes)

    AppElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(space4)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconPrimary(
                imageVector = feature.icon,
                contentDescription = title,
                size = featureCardIconSize
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = space8)
            ) {
                TextTitleLargeNeutral80(title)
                Spacer4()
                TextBodyMediumNeutral80(subtitle)
            }
        }
    }
}
