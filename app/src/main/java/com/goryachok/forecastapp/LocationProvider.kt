package com.goryachok.forecastapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

abstract class LocationProvider(private val context: Context) {

    companion object {
        private val DEFAULT_LOCATION =
            Location("DEFAULT PROVIDER").apply { latitude = 90.0; longitude = 0.0 }

        private var currentLocation: Location? = null

        private const val numOfUpdates = 1
        private const val updateInterval = 25L
    }

    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER; numUpdates = numOfUpdates; interval =
            updateInterval
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            result?.let { locationResult ->
                locationResult.locations.let {
                    it.forEach { location ->
                        location?.let {
                            doTask(location)
                        }
                    }
                }

            }
        }
    }

    private fun updateLocation() {
        locationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun lastLocation() {
        locationProviderClient.lastLocation.addOnSuccessListener {
            currentLocation = it
        }
    }

    fun start() {
        if (permissionGranted() && isLocationEnabled()) {
            lastLocation()
            currentLocation ?: updateLocation()
        } else {
            doTask(DEFAULT_LOCATION)
        }
    }

    fun stop() {
        locationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun isLocationEnabled() =
        LocationManagerCompat.isLocationEnabled((context.getSystemService(Context.LOCATION_SERVICE) as LocationManager))

    fun permissionGranted() =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    abstract fun doTask(location: Location)
}
