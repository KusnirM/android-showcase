package mk.digital.androidshowcase.data.biometric

import android.content.Context
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface BiometricClient {
    fun enabled(): Boolean
    suspend fun authenticate(): BiometricResult
}

class BiometricClientImpl(
    private val context: Context,
) : BiometricClient {

    private val biometricManager = BiometricManager.from(context)

    private var currentActivity: FragmentActivity? = null

    fun setActivity(activity: FragmentActivity?) {
        currentActivity = activity
    }

    override fun enabled(): Boolean {
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    override suspend fun authenticate(): BiometricResult {
        val activity = currentActivity
            ?: return BiometricResult.ActivityNotAvailable

        if (!enabled()) {
            return BiometricResult.NotAvailable
        }

        return suspendCancellableCoroutine { continuation ->
            val executor = ContextCompat.getMainExecutor(context)

            val callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    if (continuation.isActive) {
                        continuation.resume(BiometricResult.Success)
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    if (continuation.isActive) {
                        val result = when (errorCode) {
                            BiometricPrompt.ERROR_USER_CANCELED,
                            BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                            BiometricPrompt.ERROR_CANCELED -> BiometricResult.Cancelled

                            else -> BiometricResult.SystemError(errString.toString())
                        }
                        continuation.resume(result)
                    }
                }

                override fun onAuthenticationFailed() {
                    // Called when biometric is valid but not recognized
                    // Don't complete yet - user can retry
                }
            }

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string..biometric_prompt_title))
                .setSubtitle(context.getString(R.string..biometric_prompt_subtitle))
                .setNegativeButtonText(context.getString(R.string..biometric_prompt_cancel))
                .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
                .build()

            val biometricPrompt = BiometricPrompt(activity, executor, callback)
            biometricPrompt.authenticate(promptInfo)

            continuation.invokeOnCancellation {
                biometricPrompt.cancelAuthentication()
            }
        }
    }
}
