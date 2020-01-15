package com.goryachok.forecastapp.ui.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val LOC_PERM_CODE = 101

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        (applicationContext as WeatherApplication).component.inject(this)
        requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        val permission = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(permission)){
                viewModel.getLocation()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun checkPermissions(permissions: Array<String>): Boolean {
        return false
    }
}


//
//while (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//== PackageManager.PERMISSION_DENIED &&
//ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//== PackageManager.PERMISSION_DENIED
//) {
//    requestPermissions(
//        arrayOf(
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ), 101
//    )
//}
//}
