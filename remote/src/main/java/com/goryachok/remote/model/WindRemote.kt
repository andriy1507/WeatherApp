package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WindRemote(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    val speed: Double
) : Parcelable, RemoteEntity()