package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.services.GeolocationListener
import javax.inject.Inject

class SplashViewModel @Inject constructor() : RepoViewModel() {
    @Inject
    override lateinit var repository: Repository

    @Inject
    lateinit var locationManager: LocationManager

    init {
        Log.d("DaggerDebug", javaClass.name)
    }

    fun getLocation() {
        val criteria = Criteria()
        criteria.apply {
            accuracy = Criteria.ACCURACY_FINE
            isSpeedRequired = false
            isAltitudeRequired = false
            isBearingRequired = false
            isCostAllowed = false
        }
        val locationListener = GeolocationListener()
        locationManager.requestSingleUpdate(criteria, locationListener, null)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel() as T
        }
    }
}