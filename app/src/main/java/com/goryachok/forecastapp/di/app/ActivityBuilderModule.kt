package com.goryachok.forecastapp.di.app

import com.goryachok.forecastapp.di.viewmodel.ViewModelModule
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}