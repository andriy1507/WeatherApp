package com.goryachok.forecastapp.repository

import android.util.Log
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


class Repository @Inject constructor(val remote: RemoteDataSource, val local: LocalDataSource) {

    private lateinit var fData: ForecastEntity
    private lateinit var wData: WeatherEntity

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

    suspend fun getWeatherDataByCity(city: String): WeatherEntity {
        val data: WeatherEntity
        when (val result = remote.getWeatherByCity(city)) {
            is Success -> {
                data = result.data
                return data
            }
            is Error -> {
                throw result.exception
            }
        }
    }

    private suspend fun getWeatherDataByCoord(lat: Float, lon: Float): WeatherEntity {
        val data: WeatherEntity
        when (val result = remote.getWeatherByCoord(lat, lon)) {
            is Success -> {
                data = result.data
                return data
            }
            is Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getForecastDataByCity(city: String): ForecastEntity {
        val data: ForecastEntity
        when (val result = remote.getForecastByCity(city)) {
            is Success -> {
                data = result.data
                return data
            }
            is Error -> {
                throw result.exception
            }
        }
    }

    private suspend fun getForecastDataByCoord(lat: Float, lon: Float): ForecastEntity {
        val data: ForecastEntity
        when (val result = remote.getForecastByCoord(lat, lon)) {
            is Success -> {
                data = result.data
                return data
            }
            is Error -> {
                throw result.exception
            }
        }
    }

    fun initializeData(lat: Float, lon: Float) {
        if (isFetchNeeded()) {
            saveRemoteDataToLocal(lat, lon)
        } else {
            getLocalData()
        }
    }

    private fun getLocalData() {
        fData = local.readForecastData()
        wData = local.readWeatherData()
    }

    private fun saveRemoteDataToLocal(lat: Float, lon: Float) {
        CoroutineScope(IO).launch {
            try {
                fData = getForecastDataByCoord(lat, lon)
                wData = getWeatherDataByCoord(lat, lon)

                local.saveWeatherData(wData)
                local.saveForecastData(fData)
            } catch (e: IOException) {
                Log.e(e::class.java.name, e.message, e)
            }
        }
    }

    private fun isFetchNeeded(): Boolean {
        return if (isLocalDataAvailable()) {
            wData = local.readWeatherData()
            fData = local.readForecastData()
            val diff = System.currentTimeMillis() / 1000 - wData.date
            diff > BuildConfig.HOUR
        } else {
            true
        }
    }

    private fun isLocalDataAvailable() = local.isDataAvailable()

    private fun isDataInitialized(): Boolean {
        return try {
            wData.city
            fData.city
            true
        } catch (e: NullPointerException) {
            false
        }
    }

    fun getCurrentWeather(): WeatherEntity {
        return wData
    }

    fun getCurrentForecast(): ForecastEntity {
        return fData
    }
}
