package com.goryachok.core.base

interface ConnectivityListener {

    fun start()

    fun stop()

    fun setOnConnectionAvailableCallback(callback: () -> Unit)

    fun setOnConnectionLostCallback(callback: () -> Unit)
}