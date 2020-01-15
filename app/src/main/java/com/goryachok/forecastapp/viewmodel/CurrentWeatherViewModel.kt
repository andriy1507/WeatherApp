package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.repository.Repository

class CurrentWeatherViewModel : RepoViewModel() {

    override lateinit var repository: Repository

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentWeatherViewModel() as T
        }
    }
}