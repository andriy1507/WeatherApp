package com.goryachok.forecastapp.model.domain

import com.google.gson.Gson
import java.lang.reflect.Type

abstract class RemoteEntity : Type {
    fun toJson(): String = Gson().toJson(this, this::class.java)
}
