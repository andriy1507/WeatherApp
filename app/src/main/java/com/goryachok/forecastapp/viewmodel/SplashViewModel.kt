package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.repository.Repository

class SplashViewModel(applicationContext: Context) : ViewModel() {

    private val repository: Repository by lazy { Repository(applicationContext) }

    private val locationManager: LocationManager by lazy {
        applicationContext.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager
    }

    private fun initialize() {
        repository.initializeData(45f, 45f)
    }

    fun isDataReady(): Boolean {
        initialize()
        return repository.isDataInitialized()
    }
}