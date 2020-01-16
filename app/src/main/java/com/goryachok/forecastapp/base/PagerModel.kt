package com.goryachok.forecastapp.base

import androidx.lifecycle.ViewModel

abstract class PagerModel :ViewModel(),RepoViewModel{
    abstract fun getDataByCity(request:String)
    abstract fun getDataByLocation()
}