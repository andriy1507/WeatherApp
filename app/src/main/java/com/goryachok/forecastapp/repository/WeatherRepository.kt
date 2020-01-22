package com.goryachok.forecastapp.repository

import android.content.Context
import android.util.Log
import com.goryachok.forecastapp.base.HOUR_MS
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.IOException


class WeatherRepository(context: Context) {

    private val remote by lazy { RemoteDataSource() }
    private val local by lazy { LocalDataSource(context) }

    private var forecastData: ForecastEntity? = null
    private var weatherData: WeatherEntity? = null

    suspend fun getWeatherDataByCity(city: String): Result<WeatherEntity> =
        runBlocking {
            val result = async { remote.getWeatherByCity(city) }
            result.await()
        }

    suspend fun getForecastDataByCity(city: String): Result<ForecastEntity> =
        runBlocking {
            val result = async { remote.getForecastByCity(city) }
            result.await()
        }

    private suspend fun getWeatherDataByCoord(lat: Float, lon: Float): Result<WeatherEntity> =
        runBlocking {
            val result = async { remote.getWeatherByCoord(lat, lon) }
            result.await()
        }

    private suspend fun getForecastDataByCoord(lat: Float, lon: Float): Result<ForecastEntity> =
        runBlocking {
            val result = async { remote.getForecastByCoord(lat, lon) }
            result.await()
        }

    fun initializeData(lat: Float, lon: Float) {
        if (isFetchNeeded()) {
            saveRemoteDataToLocal(lat, lon)
        } else {
            getLocalData()
        }
    }

    private fun getLocalData() {
        forecastData = local.readForecastData()
        weatherData = local.readWeatherData()
    }

    private fun saveRemoteDataToLocal(lat: Float, lon: Float) {
        CoroutineScope(Default).launch {
            try {
                when (val result = (getForecastDataByCoord(lat, lon))) {
                    is Success -> {
                        local.saveForecastData(result.data)
                        //TODO save to DB
                    }
                    is Error -> {
                        //TODO error handling
                    }
                }
                forecastData = (getForecastDataByCoord(lat, lon) as? Success)?.data
                forecastData?.let { local.saveForecastData(it) }
                when (val result = getWeatherDataByCoord(lat, lon)) {
                    is Success -> {
                        local.saveWeatherData(result.data)
                    }
                    is Error -> {
                        //TODO error handling
                    }
                }
                weatherData = (getWeatherDataByCoord(lat, lon) as? Success)?.data


            } catch (e: IOException) {
                Log.e(e::class.java.name, e.message, e)
            }
        }

    }

    private fun isFetchNeeded(): Boolean {
        return if (isLocalDataAvailable()) {
            weatherData = local.readWeatherData()
            forecastData = local.readForecastData()
            val diff = System.currentTimeMillis() / SECOND_MS - weatherData!!.date
            diff > HOUR_MS
        } else {
            true
        }
    }

    private fun isLocalDataAvailable() = local.isDataAvailable()

    fun getCurrentWeather(): WeatherEntity? {
        return weatherData
    }

    fun getCurrentForecast(): ForecastEntity? {
        return forecastData
    }


}
