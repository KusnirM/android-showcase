package mk.digital.androidshowcase.presentation.screen.login

import com.android.resources.NightMode
import mk.digital.androidshowcase.presentation.base.BaseScreenshotTest
import mk.digital.androidshowcase.presentation.base.StateHolder
import mk.digital.androidshowcase.presentation.base.generateParameterizedData
import org.junit.Test
import org.junit.runners.Parameterized

class LoginScreenScreenshotTest(
    stateHolder: StateHolder<LoginUiState>,
    mode: NightMode,
) : BaseScreenshotTest<LoginUiState>(stateHolder, mode) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}_{1}")
        fun data(): Collection<*> = generateParameterizedData(LoginScreenPreviewParams())
    }

    @Test
    fun loginScreen() {
        screenshot {
            LoginScreen(state = state)
        }
    }
}
