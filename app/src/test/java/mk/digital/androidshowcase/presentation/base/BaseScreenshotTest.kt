package mk.digital.androidshowcase.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.rule.LocaleRule
import mk.digital.androidshowcase.presentation.base.rule.TimezoneRule
import mk.digital.androidshowcase.presentation.foundation.AppTheme
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/** Internal wrapper for short test names */
class StateHolder<T>(val state: T, private val name: String) {
    override fun toString(): String = name
}

@RunWith(Parameterized::class)
abstract class BaseScreenshotTest<T>(
    private val stateHolder: StateHolder<T>,
    private val mode: NightMode,
    renderingMode: SessionParams.RenderingMode = SessionParams.RenderingMode.NORMAL,
    maxPercentDifference: Double = 0.01,
) {
    /** Access the actual state */
    protected val state: T get() = stateHolder.state

    @get:Rule
    val paparazzi: Paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            nightMode = mode
        ),
        renderingMode = renderingMode,
        showSystemUi = false,
        maxPercentDifference = maxPercentDifference,
    )

    @get:Rule
    val localeRule: LocaleRule = LocaleRule()

    @get:Rule
    val timezoneRule: TimezoneRule = TimezoneRule()

    fun screenshot(content: @Composable () -> Unit) {
        paparazzi.snapshot(name = getName()) {
            AppTheme {
                content()
            }
        }
    }

    private fun getName(): String = when (mode) {
        NightMode.NIGHT -> "night"
        NightMode.NOTNIGHT -> "light"
    }
}

inline fun <reified T : Any> generateParameterizedData(
    viewStatesProvider: PreviewParameterProvider<T>,
): Collection<*> {
    val viewStates = viewStatesProvider.values.toList()
    val modes = listOf(NightMode.NIGHT, NightMode.NOTNIGHT)
    val typeName = T::class.simpleName ?: "State"
    return viewStates.flatMapIndexed { index, state ->
        modes.map { mode ->
            arrayOf(StateHolder(state, "${typeName}_$index"), mode)
        }
    }
}
