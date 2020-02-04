package com.goryachok.daily

import com.goryachok.core.SECOND_MS
import java.text.SimpleDateFormat
import java.util.*

fun Long.getDate(): String {
    val format = SimpleDateFormat("dd.MM")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}