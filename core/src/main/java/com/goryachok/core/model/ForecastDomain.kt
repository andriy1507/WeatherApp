package com.goryachok.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastDomain(
    val city: String,
    val country: String,
    val weatherList: List<WeatherDomain>
) : Parcelable, DomainEntity
