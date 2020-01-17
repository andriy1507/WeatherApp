package com.goryachok.forecastapp.repository

import android.util.Log
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import javax.inject.Inject
import kotlin.reflect.KClass

class Repository @Inject constructor(
    val remote: RemoteDataSource,
    val local: LocalDataSource
) {

    companion object {
        private const val HOUR = 60 * 60

        private const val DAGGER_TAG = "DaggerDebug"
        private const val API_TAG = "ApiDebug"
    }

    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    private fun needFetch() = if (local.isDataAvailable()) {
        val lastUpdate = local.lastFetchTime()
        val now = System.nanoTime()
        now - lastUpdate > HOUR
    } else true

    suspend fun <T : RemoteEntity> getDataByCoordinates(
        lon: Float,
        lat: Float,
        type: KClass<T>
    ): T {
        return if (needFetch()) {
            val remoteData = remote.getData(type, lon = lon, lat = lat)
            local.saveData(remoteData)
            Log.d(API_TAG, remoteData.toString())
            remoteData
        } else {
            local.getData(type)
        }
    }


    suspend fun <T : RemoteEntity> getDataByCity(city: String, type: KClass<T>): T =
        remote.getData(clazz = type, city = city)

    fun isDataUpdated() =
        (local.isDataAvailable() && !needFetch())
}