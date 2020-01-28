package com.goryachok.forecastapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi

abstract class ConnectivityListener(context: Context) {

    private var _isNetworkAvailable = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
    val isNetworkAvailable
        get() = _isNetworkAvailable


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val networkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            _isNetworkAvailable = false
            onConnectionLost()
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isNetworkAvailable = true
            onConnectionAvailable()
        }
    }

    abstract fun onConnectionLost()
    abstract fun onConnectionAvailable()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun start() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}