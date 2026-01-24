package mk.digital.androidshowcase.presentation.component.spacers

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import mk.digital.androidshowcase.presentation.foundation.space
import mk.digital.androidshowcase.presentation.foundation.space12
import mk.digital.androidshowcase.presentation.foundation.space2
import mk.digital.androidshowcase.presentation.foundation.space3
import mk.digital.androidshowcase.presentation.foundation.space4
import mk.digital.androidshowcase.presentation.foundation.space6
import mk.digital.androidshowcase.presentation.foundation.space8

object RowSpacer {
    @Composable
    private fun RowScope.Spacer(height: Dp) {
        Spacer(modifier = Modifier.width(height))
    }

    @Composable
    fun RowScope.Spacer1() {
        Spacer(space)
    }

    @Composable
    fun RowScope.Spacer2() {
        Spacer(space2)
    }

    @Composable
    fun RowScope.Spacer3() {
        Spacer(space3)
    }

    @Composable
    fun RowScope.Spacer4() {
        Spacer(space4)
    }

    @Composable
    fun RowScope.Spacer6() {
        Spacer(space6)
    }


    @Composable
    fun RowScope.Spacer8() {
        Spacer(space8)
    }

    @Composable
    fun RowScope.Spacer12() {
        Spacer(space12)
    }
}
