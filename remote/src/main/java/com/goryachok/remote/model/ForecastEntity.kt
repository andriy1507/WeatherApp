package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastEntity(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<Forecast>
) : Parcelable, RemoteEntity()