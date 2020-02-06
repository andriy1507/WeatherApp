package com.goryachok.core_util.di

import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import com.goryachok.core_util.utils.ConnectivityListenerImpl
import com.goryachok.core_util.utils.LocationProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilsModule {

    @Binds
    abstract fun provideConnectivityListener(impl: ConnectivityListenerImpl): ConnectivityListener

    @Binds
    abstract fun provideLocationProvider(impl: LocationProviderImpl): LocationProvider
}