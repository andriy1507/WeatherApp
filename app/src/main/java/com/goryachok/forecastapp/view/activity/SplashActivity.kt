package com.goryachok.forecastapp.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
        private const val LOCATION_REQUEST_CODE = 102
        private const val ACTIVITY_LIFETIME = SECOND_MS
    }

    private var startTime: Long = 0

    private val defaultLocationDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(R.string.default_location_message)
            .setPositiveButton(R.string.ok_button) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    @Suppress("NewApi")
    private val locationRequestDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(R.string.permission_message)
            .setPositiveButton(R.string.ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .setOnDismissListener {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE
                )
            }
            .create()
    }

    private val unavailableLocationDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(R.string.unavailable_location_message)
            .setPositiveButton(R.string.yes_button) { _, _ ->
                startActivityForResult(
                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    LOCATION_REQUEST_CODE
                )
            }
            .setNegativeButton(R.string.no_button) { dialog, _ ->
                defaultLocationDialog.show()
                dialog.dismiss()
            }
            .setNeutralButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    //TODO Move geolocation to separate class

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                super.onLocationResult(result)
                result?.let { it ->
                    it.locations.let { locations ->
                        locations.forEach { location ->
                            location?.let {
                                viewModel.initialize(it.latitude.toFloat(), it.longitude.toFloat())
                            }
                        }
                    }
                }
            }
        }
    }

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            numUpdates = 5
            interval = SECOND_MS / 2
            fastestInterval = SECOND_MS / 4
            expirationTime = 25 / 10 * SECOND_MS
        }
    }

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
    }

    private val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SplashViewModel(applicationContext) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startTime = System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.d("permissionNotGranted")
            requestLocationPermission()
        } else {
            Timber.d("permissionGranted")
            getLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                fusedLocationClient.locationAvailability
                    .addOnSuccessListener {
                        getLocation()
                    }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                when (grantResults.first()) {
                    PackageManager.PERMISSION_GRANTED -> {
                        getLocation()
                    }
                    PackageManager.PERMISSION_DENIED -> {
                        defaultLocationDialog.show()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocationPermission() {
        if (!isPermissionGranted()) {
            locationRequestDialog.show()
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (isLocationEnabled()) {
            Timber.d("getLocation ${fusedLocationClient.lastLocation}")
            fusedLocationClient.lastLocation
                .addOnCompleteListener { task ->
                    task.addOnSuccessListener { location ->
                        location?.let {
                            passCoordinates(it)
                            stopLocationUpdates()
                            val endTime = System.currentTimeMillis()
                            val difference = endTime - startTime
                            Timber.d("Difference $difference")
                            if (difference < ACTIVITY_LIFETIME) {
                                CoroutineScope(Main).launch {
                                    delay(ACTIVITY_LIFETIME - difference)
                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                }
                            }
                        }
                        location ?: startLocationUpdates()
                    }
                    task.addOnFailureListener {
                        startLocationUpdates()
                    }
                }
        } else {
            unavailableLocationDialog.show()
        }
    }

    private fun passCoordinates(location: Location) {
        viewModel.initialize(location.latitude.toFloat(), location.longitude.toFloat())
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnSuccessListener {
            getLocation()
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun isLocationEnabled() =
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isPermissionGranted() =
        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}