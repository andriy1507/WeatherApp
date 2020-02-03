package com.goryachok.core_util.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import com.goryachok.core.App
import com.goryachok.core.business.ConnectivityListener
import javax.inject.Inject

class ConnectivityListenerImpl @Inject constructor(app: App) : ConnectivityListener {

    private var connectionAvailableCallback: (() -> Unit)? = null
    private var connectionLostCallback: (() -> Unit)? = null

    private var _isNetworkAvailable = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
    val isNetworkAvailable
        get() = _isNetworkAvailable


    private val connectivityManager =
        app.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val networkRequest =
        NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            _isNetworkAvailable = false
            connectionLostCallback?.invoke()
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isNetworkAvailable = true
            connectionAvailableCallback?.invoke()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun start() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun setOnConnectionAvailableCallback(callback: () -> Unit) {
        connectionAvailableCallback = callback
    }

    override fun setOnConnectionLostCallback(callback: () -> Unit) {
        connectionLostCallback = callback
    }
}