package com.goryachok.forecastapp.di

import com.goryachok.core.navigation.StartMainActivityAction
import com.goryachok.forecastapp.navigation.StartMainActivityImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindStartMainActivityAction(impl: StartMainActivityImpl): StartMainActivityAction
}