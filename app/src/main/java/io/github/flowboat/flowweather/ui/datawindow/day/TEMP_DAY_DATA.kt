package io.github.flowboat.flowweather.ui.datawindow.day

import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

/**
 * Created by nulldev on 12/22/17.
 */
val TEMP_DAY_DATA = listOf(
        DayDataComponent("Humidity", fakeDataGen(0 .. 100))
)

private fun fakeDataGen(range: IntRange): SortedMap<ZonedDateTime, Double> {
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

        return map
}