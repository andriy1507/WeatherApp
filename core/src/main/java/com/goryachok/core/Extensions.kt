package com.goryachok.core

import java.text.SimpleDateFormat
import java.util.*

const val SECOND_MS = 1000L
const val MINUTE_MS = SECOND_MS * 60
const val HOUR_MS = MINUTE_MS * 60

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