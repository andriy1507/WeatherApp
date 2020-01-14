package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    //TODO("Implement repository dependency injection")

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel() as T
        }
    }
}