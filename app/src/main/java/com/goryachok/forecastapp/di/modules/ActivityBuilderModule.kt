package com.goryachok.forecastapp.di.modules

import com.goryachok.forecastapp.view.activities.MainActivity
import com.goryachok.forecastapp.view.activities.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}