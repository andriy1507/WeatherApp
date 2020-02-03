package com.goryachok.core.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.goryachok.core.App
import com.goryachok.core.repository.ForecastRepository
import com.goryachok.core.repository.WeatherRepository
import com.goryachok.core.viewmodels.factories.MainViewModelFactory

interface ApplicationProvider : RepositoryProvider,
    ViewModelsProvider {

    fun inject(activity: Activity)

    fun inject(fragment: Fragment)

    fun provideApp(): App
}

interface RepositoryProvider {

    fun provideForecastRepository(): ForecastRepository

    fun provideWeatherRepository(): WeatherRepository
}

interface ViewModelsProvider {

    fun provideMainViewModelFactory(): MainViewModelFactory
}