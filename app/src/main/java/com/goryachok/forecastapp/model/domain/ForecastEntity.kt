package com.goryachok.forecastapp.model.domain


import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastEntity(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<Forecast>
) : Parcelable, RemoteEntity(){
    companion object{
        fun fromGson(string: String):ForecastEntity{
            return Gson().fromJson(string,ForecastEntity::class.java)
        }
    }
}