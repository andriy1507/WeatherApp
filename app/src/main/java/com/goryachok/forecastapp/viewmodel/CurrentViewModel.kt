package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class CurrentViewModel(applicationContext: Context) : ViewModel() {
    val repository: Repository = Repository(applicationContext)
    private val _currentData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val currentData: LiveData<WeatherEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val searchedData: LiveData<WeatherEntity>
        get() = _searchedData

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
            try {
                val weatherData = repository.getWeatherDataByCity(city)
                withContext(Main) {
                    _searchedData.postValue(weatherData)
                }
            } catch (e: IOException) {
                Log.e(this@CurrentViewModel::class.java.name, e.message, e)
            }
        }
    }

    fun getDataByCoord() {
        _currentData.postValue(repository.getCurrentWeather())
    }
}
