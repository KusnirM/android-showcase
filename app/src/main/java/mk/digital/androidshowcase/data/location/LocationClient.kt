package mk.digital.androidshowcase.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import mk.digital.androidshowcase.domain.model.Location
import kotlin.coroutines.resume

interface LocationClient {
    suspend fun lastKnown(): Location?
    fun updates(highAccuracy: Boolean = false): Flow<Location>
}

class LocationClientImpl(context: Context) : LocationClient {

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    private companion object {
        const val TAG = "LocationClientImpl"
        const val LOCATION_UPDATE_INTERVAL_MS = 5000L
        const val LOCATION_MIN_UPDATE_INTERVAL_MS = 2000L
    }

    @SuppressLint("MissingPermission")
    override suspend fun lastKnown(): Location? = suspendCancellableCoroutine { cont ->
        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d(TAG, "lastKnown: lat=${location.latitude}, lng=${location.longitude}")
                    cont.resume(Location(location.latitude, location.longitude))
                } else {
                    Log.d(TAG, "lastKnown: null (no cached location)")
                    cont.resume(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "lastKnown failed: ${e.message}", e)
                cont.resume(null)
            }
    }

    @SuppressLint("MissingPermission")
    override fun updates(highAccuracy: Boolean): Flow<Location> = callbackFlow {
        val priority = if (highAccuracy) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }

        val request = LocationRequest.Builder(priority, LOCATION_UPDATE_INTERVAL_MS)
            .setMinUpdateIntervalMillis(LOCATION_MIN_UPDATE_INTERVAL_MS)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    Log.d(TAG, "updates: lat=${location.latitude}, lng=${location.longitude}")
                    trySend(Location(location.latitude, location.longitude))
                }
            }
        }

        Log.d(TAG, "updates: starting location updates")
        fusedClient.requestLocationUpdates(request, callback, null)

        awaitClose {
            Log.d(TAG, "updates: stopping location updates")
            fusedClient.removeLocationUpdates(callback)
        }
    }
}

