package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastRemote(
    @SerializedName("city")
    val city: CityRemote,
    @SerializedName("list")
    val list: List<ForecastBodyRemote>
) : Parcelable, RemoteEntity()