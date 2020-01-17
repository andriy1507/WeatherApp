package com.goryachok.forecastapp.base

import androidx.lifecycle.ViewModel

abstract class PagerModel() : ViewModel() {
    abstract fun getDataByCity(request: String)
    abstract fun getDataByLocation()
}