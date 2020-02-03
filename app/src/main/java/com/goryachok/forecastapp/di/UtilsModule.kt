package com.goryachok.forecastapp.di

import com.goryachok.core.base.ConnectivityListener
import com.goryachok.core.base.LocationProvider
import com.goryachok.forecastapp.utils.ConnectivityListenerImpl
import com.goryachok.forecastapp.utils.LocationProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilsModule {

    @Binds
    abstract fun bindConnectivityListener(impl: ConnectivityListenerImpl): ConnectivityListener

    @Binds
    abstract fun bindLocationProvider(impl: LocationProviderImpl): LocationProvider
}