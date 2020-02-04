package com.goryachok.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val locationProvider: LocationProvider,
    private val connectivityListener: ConnectivityListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            locationProvider,
            connectivityListener
        ) as T
    }
}