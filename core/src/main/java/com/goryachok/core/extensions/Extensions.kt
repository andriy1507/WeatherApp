package com.goryachok.core.extensions

import com.goryachok.core.SECOND_MS
import java.text.SimpleDateFormat
import java.util.*

fun <T> Collection<T>.getEveryEighth() = filterIndexed { index, _ -> index % 8 == 0 }

fun Int.getTime(): String {
    val format = SimpleDateFormat("HH:mm")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}

fun Int.getDate(): String {
    val format = SimpleDateFormat("dd.MM")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}