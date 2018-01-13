package io.github.flowboat.flowweather.util

import com.kizitonwose.time.Interval

val Interval<*>.value2Dec
    get() = "%.2f".format(value)

