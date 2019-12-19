package com.goryachok.forecastapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.api.ControllerAPI
import java.io.FileNotFoundException
import java.net.URL

class OpenWeatherService : Service() {


    private var currentWeatherPrefs: String = ""
    private var hourlyWeatherPrefs: String = ""
    private var dailyWeatherPrefs: String = ""

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        ControllerAPI().start()





        return super.onStartCommand(intent, flags, startId)
    }


//
//    private fun checkLocalData(): Boolean {
//        TODO("Check if local data exists")
//        return true
//    }
//
//    private fun getLocalData() {
//        if (checkLocalData()) {
//            TODO("Save local data to variables from SharedPreferences")
//            validateLocalData()
//        }else
//            getRemoteData("Lviv")
//    }
//
//    private fun validateLocalData() {
//        TODO("Check local data timestamp")
//    }
//
//
//    private fun getRemoteData(city:String) {
//
//        currentWeatherPrefs = getApiResponse(CURRENT,city,appId)
//        hourlyWeatherPrefs = getApiResponse(HOURLY,city,appId)
//        dailyWeatherPrefs = getApiResponse(DAILY,city,appId)
//
//        validateRemoteData()
//    }
//
//    private fun validateRemoteData() {
//        TODO("Check if received data is not corrupted")
//        saveRemoteData()
//    }
//
//    private fun saveRemoteData() {
//        TODO("Save remote data to SharedPreferences")
//        getLocalData()
//    }
//
//    private fun getApiResponse(type: String, city: String, appId: String): String {
//        return try {
//            URL("https://api.openweathermap.org/data/2.5/$type?q=$city&units=metric&appid=$appId")
//                .readText(Charsets.UTF_8)
//        }catch (ex:FileNotFoundException){
//            throw FileNotFoundException()
//        }
//    }
//
//    companion object {
//        const val CURRENT = "weather"
//        const val HOURLY = "hourly"
//        const val DAILY = "daily"
//    }
//
//
}