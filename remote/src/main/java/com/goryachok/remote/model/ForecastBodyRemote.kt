package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastBodyRemote(
    @SerializedName("dt")
    val date: Int,
    @SerializedName("dt_txt")
    val dateText: String,
    @SerializedName("main")
    val mainBodyRemote: MainBodyRemote,
    @SerializedName("weather")
    val weather: List<DescriptionRemote>,
    @SerializedName("wind")
    val wind: WindRemote
) : Parcelable, RemoteEntity()