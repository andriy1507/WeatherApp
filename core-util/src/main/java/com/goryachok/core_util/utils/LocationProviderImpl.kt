package com.goryachok.core_util.utils

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
import com.goryachok.core.App
import com.goryachok.core.business.LocationProvider
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(app: App) : LocationProvider {

    private val context = app.getContext()

    private var task: ((location: Location) -> Unit)? = null

    companion object {
        private const val SECOND_MS = 1000L

        private val DEFAULT_LOCATION =
            Location("DEFAULT PROVIDER").apply { latitude = 0.0; longitude = 0.0 }

        private var currentLocation: Location? = null

        private const val numOfUpdates = 1
        private const val updateInterval =
            SECOND_MS
    }

    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
            numUpdates =
                numOfUpdates
            interval =
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
                            task?.invoke(location)
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

    override fun start() {
        if (permissionGranted() && isLocationEnabled()) {
            lastLocation()
            if (currentLocation != null) {
                currentLocation?.let {
                    task?.invoke(it)
                }
            } else {
                updateLocation()
            }
        } else {
            task?.invoke(DEFAULT_LOCATION)
        }
    }

    override fun stop() {
        locationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun isLocationEnabled() =
        LocationManagerCompat.isLocationEnabled((context.getSystemService(Context.LOCATION_SERVICE) as LocationManager))

    override fun permissionGranted() =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override fun setTask(func: ((location: Location) -> Unit)) {
        task = func
    }
}