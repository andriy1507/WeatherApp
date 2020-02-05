package com.goryachok.splash

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.core.SECOND_MS
import com.goryachok.core.business.LocationProvider
import com.goryachok.core.repository.ForecastRepository
import com.goryachok.core.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    init {
        locationProvider.apply {
            setTask { location: Location ->
                initialize(location.latitude.toFloat(), location.longitude.toFloat())
                val time = System.currentTimeMillis() - startTime
                if (time < SECOND_MS) {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(SECOND_MS - time)
                        _startData.postValue(true)
                    }
                } else {
                    _startData.postValue(true)
                }
            }
        }
    }

    private val _startData: MutableLiveData<Boolean> = MutableLiveData()
    val startData: LiveData<Boolean>
        get() = _startData

    var startTime = 0L

    fun startLocationProvider() {
        locationProvider.start()
    }

    fun stopLocationProvider() {
        locationProvider.stop()
    }

    fun isLocationEnabled() = locationProvider.isLocationEnabled()

    fun isPermissionGranted() = locationProvider.permissionGranted()

    private fun initialize(lat: Float, lon: Float) {
        CoroutineScope(IO).launch {
            try {
                weatherRepository.getDataByCoordinates(lat, lon)
                forecastRepository.getDataByCoordinates(lat, lon)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val wr: WeatherRepository,
        private val fr: ForecastRepository,
        private val lp: LocationProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel(
                wr,
                fr,
                lp
            ) as T
        }
    }
}