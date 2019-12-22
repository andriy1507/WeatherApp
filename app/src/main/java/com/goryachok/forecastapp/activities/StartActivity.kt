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

    private lateinit var locationListener:LocationListener
    private lateinit var locationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.title = ""

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            while (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 1)
            }
        }

        locationListener = GeolocationListener()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        while(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            startActivityForResult(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),0)
        }

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2500,
                10F,
                locationListener
            )
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                2500,
                10F,
                locationListener
            )
        }


        geoLocation = Location(
            locationManager.getLastKnownLocation(
                locationManager.getBestProvider(Criteria(), false)!!
            )
        )




        val intent = Intent(this, MainActivity::class.java)
        val serviceIntent = Intent(this, ApiService::class.java)
        CoroutineScope(IO).launch {
            startService(serviceIntent)
            withContext(Main) {
                delay(2500)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        locationManager.removeUpdates(locationListener)
        super.onDestroy()
    }
}
