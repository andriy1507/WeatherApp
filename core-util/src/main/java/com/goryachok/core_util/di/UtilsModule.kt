package com.goryachok.core_util.di

import com.goryachok.core.base.ConnectivityListener
import com.goryachok.core.base.LocationProvider
import com.goryachok.core_util.ConnectivityListenerImpl
import com.goryachok.core_util.LocationProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilsModule {

    @Binds
    abstract fun bindConnectivityListener(impl: ConnectivityListenerImpl): ConnectivityListener

    @Binds
    abstract fun bindLocationProvider(impl: LocationProviderImpl): LocationProvider
}