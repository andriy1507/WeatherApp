package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.repository.Repository

class SplashViewModel(applicationContext: Context) : ViewModel() {

    private val repository: Repository by lazy { Repository(applicationContext) }

    @SuppressLint("MissingPermission")
    fun initialize(lat: Float, lon: Float) {
        repository.initializeData(lat, lon)
    }
}