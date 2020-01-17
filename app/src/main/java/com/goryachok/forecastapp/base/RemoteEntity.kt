package com.goryachok.forecastapp.base

import com.google.gson.Gson

abstract class RemoteEntity {
    fun toJson(): String = Gson().toJson(this, this::class.java)
}
