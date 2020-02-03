package com.goryachok.core.business

interface ConnectivityListener {

    fun start()

    fun stop()

    fun setOnConnectionAvailableCallback(callback: () -> Unit)

    fun setOnConnectionLostCallback(callback: () -> Unit)
}