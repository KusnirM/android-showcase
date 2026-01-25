package mk.digital.androidshowcase.presentation.screen.home

import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.BaseScreenshotTest
import mk.digital.androidshowcase.presentation.base.StateHolder
import mk.digital.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class HomeScreenScreenshotTest(
    stateHolder: StateHolder<HomeUiState>,
    mode: NightMode,
) : BaseScreenshotTest<HomeUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}_{1}")
        fun data(): Collection<*> = generateParameterizedData(HomeScreenPreviewParams())
    }

    @Test
    fun homeScreen() {
        screenshot {
                HomeScreen(state = state)
        }
    }
}
