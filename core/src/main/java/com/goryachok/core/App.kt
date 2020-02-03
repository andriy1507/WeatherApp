package com.goryachok.core

import android.content.Context
import com.goryachok.core.di.ApplicationProvider

interface App {

    val component: ApplicationProvider

    fun getContext(): Context
}