package io.github.flowboat.flowweather.ui.alphabase

import io.github.flowboat.flowweather.data.weather.Season
import io.github.flowboat.flowweather.ui.alphabase.horiz.DetailItem
import io.github.flowboat.flowweather.ui.alphabase.horiz.DetailType
import org.openweathermap.api.model.AbstractWeatherInformation
import org.threeten.bp.ZonedDateTime

fun AbstractWeatherInformation.toDetails(date: ZonedDateTime) = listOf(
        DetailType.PRESSURE to mainParameters.pressure.toString(),
        DetailType.HUMIDITY to mainParameters.humidity.toString(),
        DetailType.WIND_DIR to degreeToCardinal(wind.direction.degree.toDouble()),
        DetailType.WIND_SPEED to wind.speed.toString(),
        DetailType.PRECIPITATION to ((rain?.threeHours ?: 0.0) + (snow?.threeHours ?: 0.0)).toString(),
        DetailType.SEASON to Season.fromDate(date).displayName
).map { (f, s) -> DetailItem(f, s) }

fun degreeToCardinal(degree: Double): String {
    val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW", "N")
    return directions[Math.round(degree % 360 / 45).toInt()]
}
