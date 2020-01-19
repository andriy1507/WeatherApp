package com.goryachok.forecastapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import javax.inject.Inject

class CurrentViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _data: MutableLiveData<WeatherEntity> = MutableLiveData()
    val data: LiveData<WeatherEntity>
        get() = _data

    fun init() {
        Log.d(BuildConfig.TAG, this::class.java.name)
    }
}
