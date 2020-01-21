package com.goryachok.forecastapp.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestLocationPermission()
        else
            startMainActivity()

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
                when (grantResults[0]) {
                    PackageManager.PERMISSION_GRANTED -> startMainActivity()
                    PackageManager.PERMISSION_DENIED -> informUserAndRequestPermission()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // onPermissionNotAllowed
            //TODO show hint dialog
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            startMainActivity()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun informUserAndRequestPermission() {
        //TODO Create dialog
    }

    @SuppressLint("MissingPermission")
    private fun startMainActivity() {
        val locationManger = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var lastKnownLocation: Location? =
            locationManger.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            viewModel.initialize(
                lastKnownLocation.latitude.toFloat(),
                lastKnownLocation.longitude.toFloat()
            )
        } else {
            locationManger.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                200F,
                object : LocationListener {

                    override fun onLocationChanged(location: Location?) {
                        location?.let {
                            viewModel.initialize(
                                it.latitude.toFloat(),
                                it.longitude.toFloat()
                            )
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String?) {
                        lastKnownLocation =
                            locationManger.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        lastKnownLocation?.let {
                            viewModel.initialize(
                                it.latitude.toFloat(),
                                it.longitude.toFloat()
                            )
                        }
                    }

                    override fun onProviderDisabled(provider: String?) {
                        Toast.makeText(
                            this@SplashActivity,
                            "Enable your location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
        startActivity(Intent(this, MainActivity::class.java))
    }
}