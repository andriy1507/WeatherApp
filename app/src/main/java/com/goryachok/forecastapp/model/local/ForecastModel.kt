package com.goryachok.forecastapp.model.local

import com.goryachok.forecastapp.model.domain.Forecast

data class ForecastModel(
    val list: List<Forecast>
)