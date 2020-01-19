package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    val repository: Repository,
    val context: Context
) : ViewModel() {

    private val locationManager: LocationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val _data: MutableLiveData<WeatherEntity> = MutableLiveData()
    val data: LiveData<WeatherEntity>
        get() = _data

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

    fun getData() {
        var entity: WeatherEntity
        CoroutineScope(IO).launch {
            try {
                entity = repository.getWeatherData("Lviv")
                withContext(Main) {
                    _data.postValue(entity)
                }
            } catch (e: Exception) {
                Log.e(BuildConfig.TAG, e::class.java.name, e)
            }
        }
    }
}