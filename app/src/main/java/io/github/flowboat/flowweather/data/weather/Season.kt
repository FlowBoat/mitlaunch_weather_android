package io.github.flowboat.flowweather.data.weather

import org.threeten.bp.ZonedDateTime

enum class Season {
    SUMMER, WINTER, FALL, SPRING;

    val displayName = name.toLowerCase().capitalize()

    companion object {
        fun fromDate(date: ZonedDateTime): Season {
            val monthDay = date.monthValue * 100 + date.dayOfMonth
            return when {
                monthDay <= 315 -> WINTER
                monthDay <= 615 -> SPRING
                monthDay <= 915 -> SUMMER
                monthDay <= 1215 -> FALL
                else -> WINTER
            }
        }
    }
}