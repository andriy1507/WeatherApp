package com.goryachok.forecastapp.utils

fun convertDegreesToDirection(deg: Int): String =
    when (deg) {
        in 0..23, in 339..360 -> "N"
        in 24..68 -> "NE"
        in 69..113 -> "E"
        in 114..158 -> "SE"
        in 159..203 -> "S"
        in 204..248 -> "SW"
        in 249..293 -> "W"
        in 294..338 -> "NW"
        else -> "-"
    }