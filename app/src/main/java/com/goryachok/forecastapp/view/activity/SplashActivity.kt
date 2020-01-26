package com.goryachok.forecastapp.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.LocationProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
        private const val LOCATION_REQUEST_CODE = 102
    }

    private val mainActivityIntent by lazy {
        Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK }
    }

    private val enableLocationDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage(R.string.unavailable_location_message)
            .setPositiveButton(R.string.ok_button) { _, _ ->
                startActivityForResult(
                    Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    LOCATION_REQUEST_CODE
                )
            }
            .setNegativeButton(R.string.no_button) { dialog, _ ->
                locationProvider.start()
                dialog.dismiss()
            }.create()
    }

    private var startTime = 0L

    private val locationProvider by lazy {
        object : LocationProvider(this@SplashActivity) {
            override fun doTask(location: Location) {
                viewModel.initialize(location.latitude.toFloat(), location.longitude.toFloat())
                val time = System.currentTimeMillis() - startTime
                if (time < SECOND_MS) {
                    CoroutineScope(Main).launch {
                        delay(SECOND_MS - time)
                        startActivity(mainActivityIntent)
                    }
                } else {
                    startActivity(mainActivityIntent)
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
        if (!locationProvider.permissionGranted()) {
            //TODO inform user about location usage
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            if (locationProvider.isLocationEnabled()) {
                locationProvider.start()
                startTime = System.currentTimeMillis()
            } else {
                enableLocationDialog.show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                if (locationProvider.isLocationEnabled()) {
                    locationProvider.start()
                    startTime = System.currentTimeMillis()
                } else {
                    enableLocationDialog.show()
                }
            }
            //TODO inform about default location
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        enableLocationDialog.dismiss()
        if (requestCode == LOCATION_REQUEST_CODE && locationProvider.isLocationEnabled()) {

            locationProvider.start()
            startTime = System.currentTimeMillis()
        }
        //TODO inform about default location
    }

    override fun onDestroy() {
        super.onDestroy()
        locationProvider.stop()
    }
}