package com.goryachok.core_util

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goryachok.core.base.ForecastRepository
import com.goryachok.core.base.LocationProvider
import com.goryachok.core.base.WeatherRepository
import com.goryachok.core.base.viewmodels.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModelImpl : SplashViewModel() {

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

    companion object {
        private const val SECOND_MS = 1000L
    }

    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var forecastRepository: ForecastRepository

    private val _startData: MutableLiveData<Boolean> = MutableLiveData()
    val startData: LiveData<Boolean>
        get() = _startData

    @Inject
    lateinit var locationProvider: LocationProvider

    var startTime = 0L

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
}