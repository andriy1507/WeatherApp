package com.goryachok.forecastapp.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastResponse(
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("list")
    val list: List<Forecast> = listOf(Forecast())
) : Parcelable, RemoteEntity