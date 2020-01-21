package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class HourlyViewModel(context: Context) : ViewModel() {

    private val repository: Repository = Repository(context)

    private val _currentData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val currentData: LiveData<ForecastEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val searchedData: LiveData<ForecastEntity>
        get() = _searchedData

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (val result = repository.getForecastDataByCity(city)) {
                    is Result.Success -> withContext(Dispatchers.Main) {
                        _searchedData.postValue(
                            result.data
                        )
                    }
                    is Result.Error -> {
                    }
                }
            } catch (e: IOException) {
                Log.e(this@HourlyViewModel::class.java.name, e.message, e)
            }
        }
    }

    fun getDataByCoord() {
        _currentData.postValue(repository.getCurrentForecast())
    }

}
