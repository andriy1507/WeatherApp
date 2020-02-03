package com.goryachok.core_util.di.utils

import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import com.goryachok.core.di.ApplicationProvider
import dagger.Component

interface UtilsProvider {

    fun provideLocationProvider(): LocationProvider

    fun provideConnectivityListener(): ConnectivityListener
}

@Component(modules = [UtilsModule::class], dependencies = [ApplicationProvider::class])
interface UtilsComponent : UtilsProvider {

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider)

        fun build(): UtilsComponent
    }
}