package com.app.locationmanager

import android.location.Location

internal object LocationUtils {
    private const val TEN_SECONDS = 1000 * 10

    /**
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    fun isBetterLocation(location: Location, currentBestLocation: Location?): Boolean {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }

        // Check whether the new location fix is newer or older
        val timeDelta = location.time - currentBestLocation.time
        val isSignificantlyNewer = timeDelta > TEN_SECONDS
        val isSignificantlyOlder = timeDelta < -TEN_SECONDS
        val isNewer = timeDelta > 0

         // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new location fix is more or less accurate
        val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200

        // Check if the old and new location are from the same provider
        val isFromSameProvider = isSameProvider(location.provider, currentBestLocation.provider)

        // Determine location quality using a combination of timeliness and accuracy
        return if (isMoreAccurate) {
            true
        } else if (isNewer && !isLessAccurate) {
            true
        } else isNewer && !isSignificantlyLessAccurate && isFromSameProvider
    }

    /**
     * Checks whether two providers are the same
     */
    private fun isSameProvider(provider1: String?, provider2: String?): Boolean {
        return if (provider1 == null) {
            provider2 == null
        } else provider1 == provider2
    }
}