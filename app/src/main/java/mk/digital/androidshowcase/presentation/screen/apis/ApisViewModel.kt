package mk.digital.androidshowcase.presentation.screen.apis

import androidx.annotation.StringRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import mk.digital.androidshowcase.R
import mk.digital.androidshowcase.data.biometric.BiometricResult
import mk.digital.androidshowcase.domain.model.Location
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.domain.useCase.biometric.AuthenticateWithBiometricUseCase
import mk.digital.androidshowcase.domain.useCase.biometric.IsBiometricEnabledUseCase
import mk.digital.androidshowcase.domain.useCase.location.GetLastKnownLocationUseCase
import mk.digital.androidshowcase.domain.useCase.location.ObserveLocationUpdatesUseCase
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.base.NavEvent
import javax.inject.Inject

@HiltViewModel
class ApisViewModel @Inject constructor(
    private val isBiometricEnabledUseCase: IsBiometricEnabledUseCase,
    private val authenticateWithBiometricUseCase: AuthenticateWithBiometricUseCase,
    private val getLastKnownLocationUseCase: GetLastKnownLocationUseCase,
    private val observeLocationUpdatesUseCase: ObserveLocationUpdatesUseCase,
) : BaseViewModel<ApisUiState>(ApisUiState()) {

    private var locationUpdatesJob: Job? = null

    override fun loadInitialData() {
        execute(
            action = { isBiometricEnabledUseCase() },
            onSuccess = { enabled -> newState { it.copy(biometricsAvailable = enabled) } }
        )
    }

    fun share() {
        navigate(ApisNavEvent.Share(R.string.platform_apis_demo_share_text))
    }

    fun dial() {
        navigate(ApisNavEvent.Dial(R.string.platform_apis_demo_phone))
    }

    fun openLink() {
        navigate(ApisNavEvent.OpenLink(R.string.platform_apis_demo_url))
    }

    fun sendEmail() {
        navigate(
            ApisNavEvent.SendEmail(
                R.string.platform_apis_demo_email,
                R.string.platform_apis_demo_email_subject,
                R.string.platform_apis_demo_email_body
            )
        )
    }

    fun copyToClipboard() {
        navigate(ApisNavEvent.CopyToClipboard(R.string.platform_apis_demo_copy_text))
        newState { it.copy(copiedToClipboard = true) }
    }

    fun resetCopyState() {
        newState { it.copy(copiedToClipboard = false) }
    }

    fun getLocation() {
        execute(
            action = { getLastKnownLocationUseCase() },
            onLoading = { newState { it.copy(locationLoading = true, locationError = false) } },
            onSuccess = { location ->
                newState { it.copy(location = location, locationLoading = false) }
            },
            onError = {
                newState { it.copy(locationLoading = false, locationError = true) }
            }
        )
    }

    override fun onResume() {
        requireState { state -> if (state.shouldTrackLocation) startLocationUpdates() }
    }

    override fun onPause() {
        requireState { currentState -> newState { it.copy(shouldTrackLocation = currentState.isTrackingLocation) } }
        stopLocationUpdates()
    }

    fun startLocationUpdates() {
        if (locationUpdatesJob?.isActive == true) return
        newState { it.copy(isTrackingLocation = true, locationUpdatesError = false) }
        locationUpdatesJob = observe(
            flow = observeLocationUpdatesUseCase(ObserveLocationUpdatesUseCase.Params(highAccuracy = true)),
            onEach = { location -> newState { it.copy(trackedLocation = location) } },
            onError = { newState { it.copy(isTrackingLocation = false, locationUpdatesError = true) } }
        )
    }

    fun stopLocationUpdates() {
        locationUpdatesJob?.cancel()
        locationUpdatesJob = null
        newState { it.copy(isTrackingLocation = false) }
    }

    fun authenticateWithBiometrics() {
        execute(
            action = { authenticateWithBiometricUseCase() },
            onLoading = { newState { it.copy(biometricsLoading = true, biometricsResult = null) } },
            onSuccess = { result -> newState { it.copy(biometricsLoading = false, biometricsResult = result) } },
            onError = { error ->
                newState {
                    it.copy(
                        biometricsLoading = false,
                        biometricsResult = BiometricResult.SystemError(error.message)
                    )
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }
}

data class ApisUiState(
    val copiedToClipboard: Boolean = false,
    val location: Location? = null,
    val locationLoading: Boolean = false,
    val locationError: Boolean = false,
    val isTrackingLocation: Boolean = false,
    val shouldTrackLocation: Boolean = false,
    val trackedLocation: Location? = null,
    val locationUpdatesError: Boolean = false,
    val biometricsAvailable: Boolean = false,
    val biometricsLoading: Boolean = false,
    val biometricsResult: BiometricResult? = null,
)

sealed interface ApisNavEvent : NavEvent {
    data class Share(@param:StringRes val textRes: Int) : ApisNavEvent
    data class Dial(@param:StringRes val numberRes: Int) : ApisNavEvent
    data class OpenLink(@param:StringRes val urlRes: Int) : ApisNavEvent
    data class SendEmail(
        @param:StringRes val toRes: Int,
        @param:StringRes val subjectRes: Int,
        @param:StringRes val bodyRes: Int
    ) : ApisNavEvent
    data class CopyToClipboard(@param:StringRes val textRes: Int) : ApisNavEvent
}
