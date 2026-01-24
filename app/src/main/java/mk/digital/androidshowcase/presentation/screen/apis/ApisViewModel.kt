package mk.digital.androidshowcase.presentation.screen.apis

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import mk.digital.androidshowcase.data.biometric.BiometricResult
import mk.digital.androidshowcase.domain.model.Location
import mk.digital.androidshowcase.domain.repository.BiometricRepository
import mk.digital.androidshowcase.domain.repository.LocationRepository
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import mk.digital.androidshowcase.presentation.base.NavEvent
import javax.inject.Inject

@HiltViewModel
class ApisViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val biometricRepository: BiometricRepository,
) : BaseViewModel<ApisUiState>(ApisUiState()) {

    private var locationUpdatesJob: Job? = null

    override fun loadInitialData() {
        newState { it.copy(biometricsAvailable = biometricRepository.enabled()) }
    }

    fun share() {
        navigate(ApisNavEvent.Share(DEMO_SHARE_TEXT))
    }

    fun dial() {
        navigate(ApisNavEvent.Dial(DEMO_PHONE_NUMBER))
    }

    fun openLink() {
        navigate(ApisNavEvent.OpenLink(DEMO_URL))
    }

    fun sendEmail() {
        navigate(ApisNavEvent.SendEmail(DEMO_EMAIL, DEMO_EMAIL_SUBJECT, DEMO_EMAIL_BODY))
    }

    fun copyToClipboard() {
        navigate(ApisNavEvent.CopyToClipboard(DEMO_COPY_TEXT))
        newState { it.copy(copiedToClipboard = true) }
    }

    fun resetCopyState() {
        newState { it.copy(copiedToClipboard = false) }
    }

    fun getLocation() {
        execute(
            action = { locationRepository.lastKnownLocation() },
            onLoading = { newState { it.copy(locationLoading = true, locationError = false) } },
            onSuccess = { location ->
                newState { it.copy(location = location, locationLoading = false) }
            },
            onError = {
                newState { it.copy(locationLoading = false, locationError = true) }
            }
        )
    }

    fun onResumed() {
        requireState { state -> if (state.shouldTrackLocation) startLocationUpdates() }
    }

    fun onPaused() {
        requireState { currentState -> newState { it.copy(shouldTrackLocation = currentState.isTrackingLocation) } }
        stopLocationUpdates()
    }

    fun startLocationUpdates() {
        if (locationUpdatesJob?.isActive == true) return
        newState { it.copy(isTrackingLocation = true, locationUpdatesError = false) }
        locationUpdatesJob = observe(
            flow = locationRepository.locationUpdates(highAccuracy = true),
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
            action = { biometricRepository.authenticate() },
            onLoading = { newState { it.copy(biometricsLoading = true, biometricsResult = null) } },
            onSuccess = { result -> newState { it.copy(biometricsLoading = false, biometricsResult = result) } },
            onError = { error -> newState { it.copy(biometricsLoading = false, biometricsResult = BiometricResult.SystemError(error.message.orEmpty())) } }
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    private companion object {
        private const val DEMO_PHONE_NUMBER = "+1234567890"
        private const val DEMO_URL = "https://github.com/anthropics/claude-code"
        private const val DEMO_EMAIL = "example@example.com"
        private const val DEMO_EMAIL_SUBJECT = "Hello from KMP Showcase"
        private const val DEMO_EMAIL_BODY = "This is a demo email sent from the KMP Showcase app."
        private const val DEMO_SHARE_TEXT = "Check out KMP Showcase - a Kotlin Multiplatform demo app!"
        private const val DEMO_COPY_TEXT = "Text copied from KMP Showcase"
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
    data class Share(val text: String) : ApisNavEvent
    data class Dial(val number: String) : ApisNavEvent
    data class OpenLink(val url: String) : ApisNavEvent
    data class SendEmail(val to: String, val subject: String, val body: String) : ApisNavEvent
    data class CopyToClipboard(val text: String) : ApisNavEvent
}
