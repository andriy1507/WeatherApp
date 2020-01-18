package com.goryachok.forecastapp.data

import android.content.Context
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import javax.inject.Inject

class LocalDataSource{

    @Inject
    lateinit var context: Context

    init {
        DaggerRepositoryComponent.create().inject(this)
    }
}
