package com.goryachok.core_util.viewmodelfactories

import androidx.lifecycle.ViewModel
import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import com.goryachok.core.viewmodels.factories.MainViewModelFactory
import com.goryachok.core_util.viewmodels.MainViewModelImpl
import javax.inject.Inject

class MainViewModelFactoryImpl @Inject constructor(
    private val locationProvider: LocationProvider,
    private val connectivityListener: ConnectivityListener
) : MainViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModelImpl(locationProvider, connectivityListener) as T
    }
}