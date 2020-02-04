package com.goryachok.forecastapp.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.goryachok.core.navigation.StartMainActivityAction
import com.goryachok.main.MainActivity

class StartMainActivityImpl : StartMainActivityAction {

    override fun start(context: Context, args: Bundle?) {
        Intent(context, MainActivity::class.java).apply {
            args?.let { putExtras(it) }
            context.startActivity(this)
        }
    }
}