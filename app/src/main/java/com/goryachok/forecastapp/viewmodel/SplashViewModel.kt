package com.goryachok.forecastapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.ForecastRepository
import com.goryachok.forecastapp.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel(applicationContext: Context) : ViewModel() {

    private val TAG = this::class.java.simpleName

    private val weatherRepository: BaseRepository<WeatherEntity> by lazy {
        WeatherRepository(
            applicationContext
        )
    }

    private val forecastRepository: ForecastRepository by lazy {
        ForecastRepository(applicationContext)
    }

    fun initialize(lat: Float, lon: Float) {
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