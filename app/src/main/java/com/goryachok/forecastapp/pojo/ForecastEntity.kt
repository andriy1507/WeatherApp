package com.goryachok.forecastapp.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastEntity(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<Forecast>
) : Parcelable, RemoteEntity