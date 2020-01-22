package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import timber.log.Timber

class CurrentViewModel(context: Context) : ViewModel() {

    private val repository: WeatherRepository = WeatherRepository(context)

    private val _currentData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val currentData: LiveData<WeatherEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val searchedData: LiveData<WeatherEntity>
        get() = _searchedData

    @SuppressLint("MissingPermission")
    private val location =
        (context.getSystemService(LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.GPS_PROVIDER
        )

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
            try {
                when (val result = repository.getWeatherDataByCity(city)) {
                    is Result.Success -> withContext(Main) { _searchedData.postValue(result.data) }
                    is Result.Error -> {
                    }
                }
            } catch (e: IOException) {
                Timber.e(e.message, e)
            }
        }
    }

    fun getDataByCoord() {
        initializeData()
        _currentData.postValue(repository.getCurrentWeather())
    }

    private fun initializeData() {
        repository.initializeData(
            this.location.latitude.toFloat(),
            this.location.longitude.toFloat()
        )
    }
}
