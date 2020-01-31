package com.goryachok.core.base

import android.location.Location

interface LocationProvider {

    fun start()

    fun stop()

    fun isLocationEnabled(): Boolean

    fun permissionGranted(): Boolean

    fun setTask(func: ((location: Location) -> Unit))
}