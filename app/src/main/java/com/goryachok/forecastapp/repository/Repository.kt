package com.goryachok.forecastapp.repository

import android.content.Context
import android.util.Log
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okio.IOException


class Repository(val context: Context) {

    private val remote by lazy { RemoteDataSource() }
    private val local by lazy { LocalDataSource(context) }

    private lateinit var fData: ForecastEntity
    private lateinit var wData: WeatherEntity

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
//                fData = getForecastDataByCoord(lat, lon)
//                wData = getWeatherDataByCoord(lat, lon)
                wData = getWeatherDataByCity("Lviv")
                fData = getForecastDataByCity("Lviv")

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

    fun isDataInitialized(): Boolean {
        return ::wData.isInitialized && ::fData.isInitialized
    }

    fun getCurrentWeather(): WeatherEntity {
        return wData
    }

    fun getCurrentForecast(): ForecastEntity {
        return fData
    }
}
