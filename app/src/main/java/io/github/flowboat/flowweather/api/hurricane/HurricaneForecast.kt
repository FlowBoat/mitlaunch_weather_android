package io.github.flowboat.flowweather.api.hurricane

import org.openweathermap.api.model.forecast.daily.DailyForecast
import org.threeten.bp.ZonedDateTime

data class HurricaneFutureForecast(override val issueDate: ZonedDateTime,
                                   override val location: Int,
                                   val forecasts: List<DailyForecast>) : HurricaneData
