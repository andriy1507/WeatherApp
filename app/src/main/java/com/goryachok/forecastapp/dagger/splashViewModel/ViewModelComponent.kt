package com.goryachok.forecastapp.dagger.splashViewModel

import com.goryachok.forecastapp.activities.SplashActivity
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun getViewModel(): SplashViewModel
    fun inject(activity: SplashActivity)
}