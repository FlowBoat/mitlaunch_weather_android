package io.github.flowboat.flowweather.api.hurricane

import org.openweathermap.api.model.currentweather.CurrentWeather
import org.openweathermap.api.model.forecast.hourly.HourlyForecast
import org.threeten.bp.ZonedDateTime

data class HurricaneTodayForecast(override val issueDate: ZonedDateTime,
                                  override val location: Int,
                                  val forecasts: List<HourlyForecast>,
                                  val current: CurrentWeather) : HurricaneData