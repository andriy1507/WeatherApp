package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.repository.WeatherRepository

class SplashViewModel(applicationContext: Context) : ViewModel() {

    private val repository: WeatherRepository by lazy { WeatherRepository(applicationContext) }

    @SuppressLint("MissingPermission")
    fun initialize(lat: Float, lon: Float) {
        repository.initializeData(lat, lon)
    }
}