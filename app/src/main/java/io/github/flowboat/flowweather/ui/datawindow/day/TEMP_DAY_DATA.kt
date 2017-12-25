package io.github.flowboat.flowweather.ui.datawindow.day

import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

/**
 * Created by nulldev on 12/22/17.
 */
fun TEMP_DAY_DATA() = listOf(
        fakeDataGen("Humidity", 0 .. 100),
        fakeDataGen("Pressure", 87 .. 108),
        fakeDataGen("Temp", -50 .. 50),
        fakeDataGen("Wind speed", 0 .. 150),
        fakeDataGen("Precipitation", 0 .. 100),
        fakeDataGen("Air quality", 1 .. 10)
)

private fun fakeDataGen(name: String, range: IntRange): DayDataComponent {
        val map = sortedMapOf<ZonedDateTime, Double>()

        val initial = ZonedDateTime.of(2018,
                1,
                1,
                1,
                0,
                0,
                0,
                ZoneId.of("UTC"))

        val end = initial.plusDays(1)

        var current = initial
        while(current.isBefore(end)) {
                map.put(current, range.shuffled().first().toDouble())
                current = current.plusHours(1)
        }

        return DayDataComponent(name, map, range)
}