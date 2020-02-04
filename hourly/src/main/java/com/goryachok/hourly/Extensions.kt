package com.goryachok.hourly

import com.goryachok.core.SECOND_MS
import java.text.SimpleDateFormat
import java.util.*

fun Long.getTime(): String {
    val format = SimpleDateFormat("HH:mm")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}