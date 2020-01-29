package com.goryachok.forecastapp.di.app

import com.goryachok.forecastapp.di.viewmodel.ViewModelModule
import com.goryachok.forecastapp.view.fragment.CurrentFragment
import com.goryachok.forecastapp.view.fragment.DailyFragment
import com.goryachok.forecastapp.view.fragment.HourlyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCurrentFragment(): CurrentFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeHourlyFragment(): HourlyFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeDailyFragment(): DailyFragment
}