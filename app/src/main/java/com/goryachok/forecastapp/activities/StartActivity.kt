package com.goryachok.forecastapp.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.services.ApiService
import com.goryachok.forecastapp.services.GeolocationListener
import com.goryachok.forecastapp.services.GeolocationListener.Companion.geoLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartActivity : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            while (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("PERMISSION",
                    (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED).toString()
                )
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 1)
                Log.i("PERMISSIONS","PERMISSION REQUESTED")
            }
        }

        locationListener = GeolocationListener()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        Log.i("LOCATION","GETTING PROVIDEr")
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            2500,
            10F,
            locationListener
        )

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            2500,
            10F,
            locationListener
        )

        geoLocation = Location(
            locationManager.getLastKnownLocation(
                locationManager.getBestProvider(Criteria(), false)
            )
        )
        val intent = Intent(this,MainActivity::class.java)
        val serviceIntent = Intent(this, ApiService::class.java)
        serviceIntent.putExtra("LON", geoLocation.longitude)
        serviceIntent.putExtra("LAT", geoLocation.latitude)
        CoroutineScope(IO).launch {
            Log.i("LOCATION","STARTING SERVICE")
            startService(serviceIntent)
            Log.i("LOCATION","SERVICE RESULT")
            withContext(Main) {
                delay(2500)
                startActivity(intent)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(locationListener)
    }
}
