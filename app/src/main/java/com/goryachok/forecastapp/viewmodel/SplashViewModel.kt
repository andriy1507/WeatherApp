package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.goryachok.forecastapp.base.RepoViewModel
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel(), RepoViewModel {

    @Inject
    override lateinit var repository: Repository

    @Inject
    lateinit var locationManager: LocationManager

    companion object {
        private const val DAGGER_TAG = "DaggerDebug"
    }

    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
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

    fun initData() {
        getLocation()
        viewModelScope.launch {
            if (GeolocationListener.geoLocation != null) {
                val loc: Location = GeolocationListener.geoLocation!!
                repository.getDataByCoordinates(
                    type = WeatherEntity::class,
                    lon = loc.longitude.toFloat(),
                    lat = loc.latitude.toFloat()
                )
                repository.getDataByCoordinates(
                    type = ForecastEntity::class,
                    lon = loc.longitude.toFloat(),
                    lat = loc.latitude.toFloat()
                )
            }
        }
    }

    fun isDataInitialized() =
        repository.isDataUpdated()

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel() as T
        }
    }
}