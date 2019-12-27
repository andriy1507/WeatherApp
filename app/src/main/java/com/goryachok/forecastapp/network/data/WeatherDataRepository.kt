package com.goryachok.forecastapp.network.data

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.goryachok.forecastapp.model.ForecastResponse
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.network.api.OpenWeatherMapAPI
import com.goryachok.forecastapp.network.connectivity.NoConnectivityException
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import retrofit2.HttpException
import java.net.SocketTimeoutException

const val PREFS_NAME = "WEATHER_PREFERENCES"
private const val LOCAL_WEATHER_PREFS_NAME = "LOCAL_WEATHER_DATA"
private const val LOCAL_FORECAST_PREFS_NAME = "LOCAL_FORECAST_DATA"

class WeatherDataRepository(private val api: OpenWeatherMapAPI, prefs: SharedPreferences) {

    var localWeatherData: MutableLiveData<WeatherResponse> = MutableLiveData()
    var localForecastData: MutableLiveData<ForecastResponse> = MutableLiveData()
    var lastSearchWeatherData: MutableLiveData<WeatherResponse> = MutableLiveData()
    var lastSearchForecastData: MutableLiveData<ForecastResponse> = MutableLiveData()

    init {

        if (prefs.contains(LOCAL_FORECAST_PREFS_NAME) && prefs.contains(
                LOCAL_WEATHER_PREFS_NAME
            )
        ) {
            localForecastData.value = Gson().fromJson(
                prefs.getString(LOCAL_FORECAST_PREFS_NAME, ""),
                ForecastResponse::class.java
            )
            localWeatherData.value = Gson().fromJson(
                prefs.getString(LOCAL_WEATHER_PREFS_NAME, ""),
                WeatherResponse::class.java
            )
        } else {
            localForecastData.value = ForecastResponse()
            localWeatherData.value = WeatherResponse()
        }
        lastSearchForecastData.value = ForecastResponse()
        lastSearchWeatherData.value = WeatherResponse()
    }

    fun getCurrentData() {
        val lat = GeolocationListener.geoLocation.latitude.toFloat()
        val lon = GeolocationListener.geoLocation.longitude.toFloat()
        CoroutineScope(IO).launch {
            try {
                val forecastResponse = api.getForecastByCoordinatesAsync(lat, lon)
                val weatherResponse = api.getWeatherByCoordinatesAsync(lat, lon)
                withContext(Main) {
                    delay(500)
                    localForecastData.postValue(forecastResponse)
                    localWeatherData.postValue(weatherResponse)
                }
            } catch (e: NoConnectivityException) {
                Log.e("Connectivity", "No internet connection", e)
            } catch (e: HttpException) {
                Log.e("Retrofit", "Invalid http request", e)
            } catch (e: SocketTimeoutException) {
                Log.e("Timeout", "Failed to connect", e)
            }
        }
    }

    fun getSearchedData(city: String) {
        CoroutineScope(IO).launch {
            try {
                val forecastResponse = api.getForecastByCityAsync(city)
                val weatherResponse = api.getWeatherByCityAsync(city)
                withContext(Main) {
                    delay(500)
                    lastSearchForecastData.postValue(forecastResponse)
                    lastSearchWeatherData.postValue(weatherResponse)
                }
            } catch (e: NoConnectivityException) {
                Log.e("Connectivity", "No internet connection", e)
            } catch (e: HttpException) {
                Log.e("Retrofit", "Invalid http request", e)
            } catch (e: SocketTimeoutException) {
                Log.e("Timeout", "Failed to connect", e)
            }
        }
    }

    fun initializeData() {
        //TODO("DO SOMETHING WITH RECEIVED TIME")
        if (isFetchNeeded(ZonedDateTime.now().minusHours(2))) {
            getCurrentData()
        }
    }

    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val oneHourAgo = ZonedDateTime.now().minusHours(1)
        return lastFetchTime.isBefore(oneHourAgo)
    }
}