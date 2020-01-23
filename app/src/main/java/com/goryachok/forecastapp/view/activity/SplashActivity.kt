package com.goryachok.forecastapp.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
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
import com.goryachok.forecastapp.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
        private const val LOCATION_REQUEST_CODE = 102
    }

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
            }
            .setOnDismissListener {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE
                )
            }
            .create()
    }


    //TODO use or delete
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

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                p0?.let { result ->
                    result.locations.let { locations ->
                        locations.forEach {
                            it?.let {
                                viewModel.initialize(it.latitude.toFloat(), it.longitude.toFloat())
                            }
                        }
                    }
                }
            }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestLocationPermission()
        } else {
            getLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                fusedLocationClient.locationAvailability.addOnSuccessListener {
                    //TODO Fix bug (When you turn on Location sometimes it doesn't show main activity)
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
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationRequestDialog.show()
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.locationAvailability.addOnSuccessListener {
            it?.let {
                if (it.isLocationAvailable) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            location?.let {
                                initializeData(location)
                            }
                        }
                        .addOnCompleteListener {
                            stopLocationUpdates()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener {
                            startLocationUpdates()
                            defaultLocationDialog.show()
                            startLocationUpdates()
                        }
                } else {
                    unavailableLocationDialog.show()
                }
            }
        }
    }

    private fun initializeData(location: Location) {
        viewModel.initialize(location.latitude.toFloat(), location.longitude.toFloat())
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            LocationRequest.create(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}