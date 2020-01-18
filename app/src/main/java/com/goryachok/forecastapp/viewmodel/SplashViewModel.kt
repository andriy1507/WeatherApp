package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SplashViewModel  : ViewModel() {

    private val _data = MutableLiveData<WeatherEntity>()
    val data: LiveData<WeatherEntity>
        get() = _data


    lateinit var repository: Repository


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
        CoroutineScope(IO).launch {
            //            if (GeolocationListener.geoLocation != null) {
//                val loc: Location = GeolocationListener.geoLocation!!
//                repository.getDataByCoordinates(
//                    type = WeatherEntity::class,
//                    lon = loc.longitude.toFloat(),
//                    lat = loc.latitude.toFloat()
//                )
//                repository.getDataByCoordinates(
//                    type = ForecastEntity::class,
//                    lon = loc.longitude.toFloat(),
//                    lat = loc.latitude.toFloat()
//                )
//            }
            _data.postValue(repository.getDataByCity("Lviv", WeatherEntity::class))
            repository.getDataByCity("Lviv", ForecastEntity::class)
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