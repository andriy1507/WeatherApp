package com.goryachok.forecastapp.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.LocationProvider
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    val weatherRepository: BaseRepository<WeatherEntity>,
    val forecastRepository: BaseRepository<ForecastEntity>,
    val locationProvider: LocationProvider
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