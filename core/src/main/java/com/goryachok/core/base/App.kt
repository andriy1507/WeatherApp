package com.goryachok.core.base

import android.content.Context
import com.goryachok.core.ApplicationProvider

interface App {

    val component: ApplicationProvider

    fun getContext(): Context
}