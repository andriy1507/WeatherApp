package com.goryachok.forecastapp.services

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class GeolocationListener:LocationListener {
    companion object{
        var geoLocation:Location? = null
    }

    override fun onLocationChanged(location: Location?) {
        geoLocation = Location(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        geoLocation = Location(provider)
    }

    override fun onProviderEnabled(provider: String?) {
        geoLocation = Location(provider)
    }

    override fun onProviderDisabled(provider: String?) {
        geoLocation = Location(provider)
    }
}