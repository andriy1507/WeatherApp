package com.goryachok.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDomain(
    val city: String,
    val description: String = "",
    val temp: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpd: Double,
    val windDir: Int,
    val timeStamp: Long
) : Parcelable, DomainEntity