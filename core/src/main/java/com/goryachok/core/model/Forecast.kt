package com.goryachok.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val city: String,
    val country: String,
    val weatherList: List<Weather>
) : Parcelable, DomainEntity
