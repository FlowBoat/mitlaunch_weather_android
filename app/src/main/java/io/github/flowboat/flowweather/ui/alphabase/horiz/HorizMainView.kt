package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.alphabase.HourItem
import io.github.flowboat.flowweather.util.random
import kotlinx.android.synthetic.main.item_horiz_main.view.*

class HorizMainView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        ConstraintLayout(context, attrs) {
    fun onCreate() {
        hourly.setHasFixedSize(true)
        hourly.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        val range = -20 .. 20

        var last = range.toList().random()!!

        val testItems = (1 .. 24).map {
            val oldLast = last

            last = range.toList().random()!!

            HourItem(it,
                    range.first.toDouble(),
                    range.last.toDouble(),
                    oldLast.toDouble(),
                    last.toDouble(),
                    it != 24)

        }

        hourly.adapter = FlexibleAdapter(testItems)

        val testDetails = listOf(
                DetailType.PRECIPITATION to "30",
                DetailType.PRESSURE to "101.43",
                DetailType.HUMIDITY to "13.8",
                DetailType.AIR_QUALITY to "4",
                DetailType.WIND_SPEED to "20",
                DetailType.WIND_DIR to "NW",
                DetailType.SEASON to "Summer",
                DetailType.UV_INDEX to "1"
        ).map { DetailItem(it.first, it.second) }

        details.setHasFixedSize(true)
        details.layoutManager = LinearLayoutManager(context)
        details.adapter = FlexibleAdapter(testDetails)
    }
}
