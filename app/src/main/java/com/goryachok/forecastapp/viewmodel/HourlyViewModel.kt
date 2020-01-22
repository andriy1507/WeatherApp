package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.Forecast
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class HourlyViewModel(context: Context) : ViewModel() {

    private val repository: WeatherRepository = WeatherRepository(context)

    private val _currentData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val currentData: LiveData<ForecastEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val searchedData: LiveData<ForecastEntity>
        get() = _searchedData

    val data = listOf(searchedData, currentData)

    @SuppressLint("MissingPermission")
    private val location =
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.GPS_PROVIDER
        )

    fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (val result = repository.getForecastDataByCity(city)) {
                    is Result.Success -> withContext(Dispatchers.Main) {
                        val data = result.data
                        data.let {
                            val newList = mutableListOf<Forecast>()
                            data.let {
                                for (index in 0..7) {
                                    newList.add(data.list[index])
                                }
                                _searchedData.postValue(data.copy(list = newList))
                            }
                        }
                    }
                    is Result.Error -> {
                    }
                }
            } catch (e: IOException) {
                Log.e(this@HourlyViewModel::class.java.name, e.message, e)
            }
        }
    }

    fun getDataByCoordinates() {
        initializeData()
        val data = repository.getCurrentForecast()

        val newList = mutableListOf<Forecast>()
        data?.let {
            for (index in 0..7) {
                newList.add(data.list[index])
            }
            _currentData.postValue(data.copy(list = newList))
        }
    }

    private fun initializeData() {
        repository.initializeData(
            this.location.latitude.toFloat(),
            this.location.longitude.toFloat()
        )
    }
}
