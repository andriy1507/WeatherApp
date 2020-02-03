package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DescriptionRemote(
    @SerializedName("description")
    val description: String
) : Parcelable, RemoteEntity()