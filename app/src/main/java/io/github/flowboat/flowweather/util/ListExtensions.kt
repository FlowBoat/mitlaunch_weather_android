package io.github.flowboat.flowweather.util

import java.util.*

private val random = Random()

fun <E> List<E>.random(): E? = if (size > 0) get(random.nextInt(size)) else null
