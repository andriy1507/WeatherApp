package com.goryachok.core.navigation

import android.content.Context
import android.os.Bundle

interface StartMainActivityAction {

    fun start(context: Context, args: Bundle? = null)
}