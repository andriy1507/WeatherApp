package com.goryachok.main

import com.goryachok.core.base.App
import com.goryachok.core.base.ForecastRepository
import com.goryachok.core.base.WeatherRepository
import com.goryachok.core_ui.base.MainActivity
import javax.inject.Inject

class MainActivityImpl : MainActivity() {

    override fun setupDependencies() {
        (applicationContext as App).component.inject(this)
    }

    @Inject
    lateinit var forecastRepository: ForecastRepository


    @Inject
    lateinit var weatherRepository: WeatherRepository
}