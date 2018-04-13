package io.github.flowboat.flowweather.api.hurricane

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import io.github.flowboat.flowweather.data.preference.PreferencesHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.openweathermap.api.UrlConnectionDataWeatherClient
import org.openweathermap.api.query.Language
import org.openweathermap.api.query.QueryBuilderPicker
import org.openweathermap.api.query.UnitFormat
import org.threeten.bp.ZonedDateTime
import java.io.File
import java.util.concurrent.TimeUnit

class Hurricane(context: Context) {
    private val gson: Gson by Kodein.global.lazy.instance()
    private val prefs: PreferencesHelper by Kodein.global.lazy.instance()

    val cityCompletor = OwmCityCompletor(context)

    private val API_KEY = "4f5c383b25336c35f187d9d3418c8c90"

    private val api = UrlConnectionDataWeatherClient(API_KEY)

    private val rootForecastDir = context.cacheDir.resolve("forecasts")
    private val futureForecastDir = rootForecastDir.resolve("future").apply { mkdirs() }
    private val todayForecastDir = rootForecastDir.resolve("today").apply { mkdirs() }

    fun currentCity(): Int { return prefs.locationCurrent.get() }

    private fun getMostRecentForecast(dir: File): Pair<ZonedDateTime, String>? {
        val list = dir.list() ?: return null

        return list.map {
            ZonedDateTime.parse(it) to it
        }.sortedByDescending { it.first }.firstOrNull()?.let {
            it.first to File(dir, it.second).readText()
        }
    }

    private inline fun <reified T : HurricaneData> writeForecast(folder: File, forecast: T) {
        folder.resolve(forecast.issueDate.toString()).writeText(gson.toJson(forecast))
    }

    @Synchronized
    fun forecastFuture(): HurricaneFutureForecast {
        val last = getMostRecentForecast(futureForecastDir)
        val now = ZonedDateTime.now()

        if(last != null
                && last.first.plusHours(REFRESH_FREQ_HOURS).isAfter(now)) {
            val res: HurricaneFutureForecast = gson.fromJson(last.second)

            if(res.location == currentCity()) return res
        }

        val newForecast = api.getForecastInformation(QueryBuilderPicker.pick()
                .forecast()
                .daily()
                .byCityId(currentCity().toString())
                .unitFormat(UnitFormat.METRIC)
                .language(Language.ENGLISH)
                .build())

        val res = HurricaneFutureForecast(now, currentCity(), newForecast.forecasts)

        writeForecast(futureForecastDir, res)

        return res
    }

    @Synchronized
    fun forecastToday(): HurricaneTodayForecast {
        val last = getMostRecentForecast(todayForecastDir)
        val now = ZonedDateTime.now()

        if(last != null
                && last.first.plusHours(REFRESH_FREQ_HOURS).isAfter(now)) {
            val res: HurricaneTodayForecast = gson.fromJson(last.second)

            if(res.location == currentCity()) return res
        }

        val hourlyForecast = api.getForecastInformation(QueryBuilderPicker.pick()
                .forecast()
                .hourly()
                .byCityId(currentCity().toString())
                .unitFormat(UnitFormat.METRIC)
                .language(Language.ENGLISH)
                .build())

        val currentForecast = api.getCurrentWeather(QueryBuilderPicker.pick()
                .currentWeather()
                .oneLocation()
                .byCityId(currentCity().toString())
                .unitFormat(UnitFormat.METRIC)
                .language(Language.ENGLISH)
                .build())

        val res = HurricaneTodayForecast(now, currentCity(), hourlyForecast.forecasts, currentForecast)

        writeForecast(todayForecastDir, res)

        return res
    }

    private fun observeForecastChanges(): Observable<Unit> {
        return Observable.interval(REFRESH_FREQ_HOURS, TimeUnit.HOURS).map {
            Unit
        }.mergeWith(prefs.locationCurrent.asObservable().map { Unit })
    }

    fun observeForecastFuture(): Observable<HurricaneFutureForecast> {
        return observeForecastChanges().subscribeOn(Schedulers.io()).map {
            forecastFuture()
        }
    }

    fun observeForecastToday(): Observable<HurricaneTodayForecast> {
        return observeForecastChanges().subscribeOn(Schedulers.io()).map {
            forecastToday()
        }
    }

    companion object {
        private const val REFRESH_FREQ_HOURS = 1L
    }
}