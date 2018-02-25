package io.github.flowboat.flowweather.util

import java.math.BigDecimal

fun Double.round(scale: Int) =
        BigDecimal(this).setScale(scale, BigDecimal.ROUND_HALF_UP).toDouble()

fun Float.round(scale: Int) =
        BigDecimal(this.toDouble()).setScale(scale, BigDecimal.ROUND_HALF_UP).toFloat()
