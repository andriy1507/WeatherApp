package com.goryachok.forecastapp

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.view.View
import com.google.android.material.snackbar.Snackbar

class ConnectivityListener(context: Context, layout: View) {
    // TODO Move constants to separate file
    var isFirstLaunch = true

    private var _isNetworkAvailable = false
    val isNetworkAvailable
        get() = _isNetworkAvailable


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            _isNetworkAvailable = false
            connectionLostSnackBar.show()
            isFirstLaunch = false
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isNetworkAvailable = true
            connectionLostSnackBar.dismiss()
            if (!isFirstLaunch) {
                connectionAvailableSnackBar.show()
            }
            isFirstLaunch = false
        }

    }
    private val connectionAvailableSnackBar by lazy {
        Snackbar.make(
            layout,
            "Connection available",
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.parseColor("#52EB34"))
            .setTextColor(Color.parseColor("#E0E0E0"))
    }

    private val connectionLostSnackBar by lazy {
        Snackbar.make(
            layout,
            "Connection lost",
            Snackbar.LENGTH_INDEFINITE
        ).setBackgroundTint(Color.parseColor("#EBC334"))
            .setTextColor(Color.parseColor("#E0E0E0"))
    }

    fun start() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}