package com.example.studienarbeitapp.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * A helper class for accessing the device's current location.
 *
 * @param context The context from which the location service is accessed.
 */
class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Retrieves the device's current location.
     * If location permissions are not granted, the callback will receive `null`.
     *
     * @param callback A function that receives the current location or `null` if the location
     *                 cannot be retrieved or if permissions are not granted.
     */
    fun getCurrentLocation(callback: (Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permissions if not granted
            callback(null)
            return
        }

        // Retrieve the last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                callback(location)
            }
            .addOnFailureListener { e ->
                // Handle failure
                e.printStackTrace()
                callback(null)
            }
    }
}