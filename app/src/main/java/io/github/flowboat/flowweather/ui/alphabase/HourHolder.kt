package io.github.flowboat.flowweather.ui.alphabase

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_hour.*
import kotlin.math.roundToInt

class HourHolder(
        val view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: HourItem) {
        val avg = ((item.begin + item.end)/2).roundToInt()
        hour_label.text = "${item.hourIndex}:00\n$avg°C"
        graph.bindData(item.rangeBegin, item.rangeEnd, item.begin, item.end, item.showEdge)
    }
}
