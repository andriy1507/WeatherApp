package com.goryachok.forecastapp.ui.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        requestLocationAndPermissions(locationManager)
        //WeatherApplication.repository.initializeData()
        GlobalScope.launch {
            delay(2500)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun requestLocationAndPermissions(locationManager: LocationManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            while (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 101
                )
            }
        }


        val locationListener = GeolocationListener()
        val providersList = listOf(LocationManager.NETWORK_PROVIDER, LocationManager.GPS_PROVIDER)
        locationManager.apply {
            for (provider in providersList) {
                if (locationManager.getProvider(provider) != null) {
                    if (locationManager.isProviderEnabled(provider)) {
                        requestLocationUpdates(provider, 60000, 1000f, locationListener)
                    }
                    GeolocationListener.geoLocation =
                        locationManager.getLastKnownLocation(provider) ?: Location(provider)
                }
            }
            removeUpdates(locationListener)
        }
    }
}
