package com.goryachok.forecastapp.view.activities

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_CODE = 101
        private const val TAG = "DaggerDebug"
    }

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        (applicationContext as WeatherApplication).component.inject(this)
        Log.d(TAG, SplashActivity::class.java.name)

        if (!checkForPermissions()) {
            askForPermissions()
        } else {
            TODO("Go to main activity if data is available")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TODO("Go to main activity if data is available")
                } else {
                    alert(getString(R.string.permission_message))
                    askForPermissions()
                }
            }
        }
    }

    private fun alert(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun checkForPermissions(): Boolean {
        return if (VERSION.SDK_INT >= VERSION_CODES.M)
            checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        else
            true
    }

    private fun askForPermissions() {
        if (VERSION.SDK_INT >= VERSION_CODES.M)
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE)
    }
}